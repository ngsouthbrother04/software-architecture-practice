package com.orderservice.application.service;

import com.orderservice.application.dto.CreateOrderRequest;
import com.orderservice.application.dto.OrderResponse;
import com.orderservice.application.port.input.CreateOrderUseCase;
import com.orderservice.domain.entity.Order;
import com.orderservice.domain.repository.OrderRepository;
import com.orderservice.infrastructure.messaging.OrderEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements CreateOrderUseCase {
    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    
    public OrderService(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerId());
        
        try {
            // 1. Create domain entity
            Order order = new Order(request.getCustomerId(), request.getTotalAmount());
            
            // 2. Validate (already done in constructor)
            
            // 3. Save to repository
            Order savedOrder = orderRepository.save(order);
            
            log.info("Order created successfully with ID: {}", savedOrder.getId());
            
            // 4. Publish event to Kafka
            eventPublisher.publishOrderCreatedEvent(savedOrder);
            
            // 5. Return response
            return mapToResponse(savedOrder);
            
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create order", e);
        }
    }
    
    @Override
    public OrderResponse getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        return mapToResponse(order);
    }

    @Transactional
    public void approveOrder(Long orderId) {
        log.info("Approving order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        order.approve();
        orderRepository.save(order);
        log.info("Order {} approved successfully", orderId);
    }
    
    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getCustomerId(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getCreatedAt()
        );
    }
}