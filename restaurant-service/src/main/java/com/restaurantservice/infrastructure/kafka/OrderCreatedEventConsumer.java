package com.restaurantservice.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantservice.application.dto.OrderCreatedEvent;
import com.restaurantservice.application.service.OrderApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(OrderCreatedEventConsumer.class);
    
    private final OrderApprovalService approvalService;
    private final ObjectMapper objectMapper;
    
    public OrderCreatedEventConsumer(OrderApprovalService approvalService) {
        this.approvalService = approvalService;
        this.objectMapper = new ObjectMapper();
    }
    
    @KafkaListener(topics = "order.created", groupId = "restaurant-service")
    public void consumeOrderCreated(String message) {
        log.info("Received order.created event: {}", message);
        
        try {
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
            
            approvalService.processOrderCreated(event.getOrderId(), event.getCustomerId());
            
        } catch (Exception e) {
            log.error("Error processing order.created event: {}", e.getMessage(), e);
            // TODO: Send to DLQ or retry
        }
    }
}
