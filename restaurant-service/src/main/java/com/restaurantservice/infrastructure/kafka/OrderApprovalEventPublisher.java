package com.restaurantservice.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantservice.domain.entity.OrderApproval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderApprovalEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(OrderApprovalEventPublisher.class);
    private static final String TOPIC_APPROVED = "order.approved";
    private static final String TOPIC_REJECTED = "order.rejected";
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public OrderApprovalEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }
    
    public void publishOrderApproved(OrderApproval approval) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("orderId", approval.getOrderId());
            event.put("customerId", approval.getCustomerId());
            event.put("status", approval.getStatus().name());
            event.put("approvedAt", approval.getUpdatedAt().toString());
            
            String eventJson = objectMapper.writeValueAsString(event);
            
            kafkaTemplate.send(TOPIC_APPROVED, String.valueOf(approval.getOrderId()), eventJson)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully sent order.approved event for order: {}", approval.getOrderId());
                    } else {
                        log.error("Failed to send order.approved event for order: {}", approval.getOrderId(), ex);
                    }
                });
                
        } catch (JsonProcessingException e) {
            log.error("Error serializing order.approved event", e);
            throw new RuntimeException("Failed to publish order.approved event", e);
        }
    }
    
    public void publishOrderRejected(OrderApproval approval) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("orderId", approval.getOrderId());
            event.put("customerId", approval.getCustomerId());
            event.put("status", approval.getStatus().name());
            event.put("rejectionReason", approval.getRejectionReason());
            event.put("rejectedAt", approval.getUpdatedAt().toString());
            
            String eventJson = objectMapper.writeValueAsString(event);
            
            kafkaTemplate.send(TOPIC_REJECTED, String.valueOf(approval.getOrderId()), eventJson)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully sent order.rejected event for order: {}", approval.getOrderId());
                    } else {
                        log.error("Failed to send order.rejected event for order: {}", approval.getOrderId(), ex);
                    }
                });
                
        } catch (JsonProcessingException e) {
            log.error("Error serializing order.rejected event", e);
            throw new RuntimeException("Failed to publish order.rejected event", e);
        }
    }
}
