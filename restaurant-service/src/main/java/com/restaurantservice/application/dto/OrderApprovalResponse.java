package com.restaurantservice.application.dto;

import java.time.LocalDateTime;

public class OrderApprovalResponse {
    private Long id;
    private Long orderId;
    private String customerId;
    private String status;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public OrderApprovalResponse() {}
    
    public OrderApprovalResponse(Long id, Long orderId, String customerId, String status, 
                                 String rejectionReason, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
