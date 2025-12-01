package com.orderservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.domain.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);
    private static final String TOPIC = "order.created";
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public OrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }
    
    public void publishOrderCreatedEvent(Order order) {
        try {
            // Create event payload
            Map<String, Object> event = new HashMap<>();
            event.put("orderId", order.getId());
            event.put("customerId", order.getCustomerId());
            event.put("totalAmount", order.getTotalAmount());
            event.put("status", order.getStatus().name());
            event.put("createdAt", order.getCreatedAt().toString());
            
            String eventJson = objectMapper.writeValueAsString(event);
            
            // Send to Kafka
            kafkaTemplate.send(TOPIC, String.valueOf(order.getId()), eventJson)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully sent order.created event for order ID: {}", order.getId());
                    } else {
                        log.error("Failed to send order.created event for order ID: {}", order.getId(), ex);
                    }
                });
                
        } catch (JsonProcessingException e) {
            log.error("Error serializing order event", e);
            throw new RuntimeException("Failed to publish order event", e);
        }
    }
}