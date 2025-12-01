package com.orderservice.infrastructure.persistence.adapter;

import com.orderservice.domain.entity.Order;
import com.orderservice.domain.repository.OrderRepository;
import com.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.orderservice.infrastructure.persistence.repository.JpaOrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryAdapter implements OrderRepository {
    
    private final JpaOrderRepository jpaRepository;
    
    public OrderRepositoryAdapter(JpaOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    // Mapping Domain <-> Entity
    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());
        return entity;
    }
    
    private Order toDomain(OrderEntity entity) {
        Order order = new Order(entity.getCustomerId(), entity.getTotalAmount());
        order.setId(entity.getId());
        order.setStatus(entity.getStatus());
        order.setCreatedAt(entity.getCreatedAt());
        return order;
    }
}