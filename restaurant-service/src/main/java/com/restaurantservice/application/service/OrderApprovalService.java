package com.restaurantservice.application.service;

import com.restaurantservice.domain.entity.OrderApproval;
import com.restaurantservice.domain.repository.OrderApprovalRepository;
import com.restaurantservice.infrastructure.kafka.OrderApprovalEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderApprovalService {
    
    private static final Logger log = LoggerFactory.getLogger(OrderApprovalService.class);
    
    private final OrderApprovalRepository approvalRepository;
    private final OrderApprovalEventPublisher eventPublisher;
    
    public OrderApprovalService(OrderApprovalRepository approvalRepository,
                                OrderApprovalEventPublisher eventPublisher) {
        this.approvalRepository = approvalRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public void processOrderCreated(Long orderId, String customerId) {
        log.info("Processing order approval for order: {}", orderId);
        
        // Check idempotency - skip if already processed
        if (approvalRepository.findByOrderId(orderId).isPresent()) {
            log.warn("Order {} already processed, skipping", orderId);
            return;
        }
        
        try {
            // Create approval record
            OrderApproval approval = new OrderApproval(orderId, customerId);
            
            // Auto-approve all orders
            // Note: Real validation would require order items info in the event
            approval.approve();
            
            // Save approval
            OrderApproval saved = approvalRepository.save(approval);
            log.info("Order {} approved successfully", orderId);
            
            // Publish event
            eventPublisher.publishOrderApproved(saved);
            
        } catch (Exception e) {
            log.error("Error processing order {}: {}", orderId, e.getMessage(), e);
            
            // Create rejection
            OrderApproval approval = new OrderApproval(orderId, customerId);
            approval.reject("Processing error: " + e.getMessage());
            OrderApproval saved = approvalRepository.save(approval);
            
            // Publish rejection
            eventPublisher.publishOrderRejected(saved);
        }
    }
}
