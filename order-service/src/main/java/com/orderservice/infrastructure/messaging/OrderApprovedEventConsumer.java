package com.orderservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.application.dto.OrderApprovedEvent;
import com.orderservice.application.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovedEventConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(OrderApprovedEventConsumer.class);
    
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    
    public OrderApprovedEventConsumer(OrderService orderService) {
        this.orderService = orderService;
        this.objectMapper = new ObjectMapper();
    }
    
    @KafkaListener(topics = "order.approved", groupId = "order-service")
    public void consumeOrderApproved(String message) {
        log.info("Received order.approved event: {}", message);
        
        try {
            // Parse message to extract orderId
            // The message format from restaurant-service is: {"orderId":123, "status":"APPROVED", ...}
            // Or it might be just the orderId as key and JSON as value. 
            // Based on restaurant-service publisher: 
            // kafkaTemplate.send(TOPIC_APPROVED, String.valueOf(approval.getOrderId()), eventJson)
            
            OrderApprovedEvent event = objectMapper.readValue(message, OrderApprovedEvent.class);
            
            if (event.getOrderId() != null) {
                orderService.approveOrder(event.getOrderId());
            } else {
                log.warn("Received order.approved event without orderId: {}", message);
            }
            
        } catch (Exception e) {
            log.error("Error processing order.approved event: {}", e.getMessage(), e);
        }
    }
}
