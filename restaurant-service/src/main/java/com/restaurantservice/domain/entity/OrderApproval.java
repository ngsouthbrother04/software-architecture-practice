package com.restaurantservice.domain.entity;

import com.restaurantservice.domain.valueobject.ApprovalStatus;
import java.time.LocalDateTime;

public class OrderApproval {
    private Long id;
    private Long orderId;
    private String customerId;
    private ApprovalStatus status;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public OrderApproval(Long orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = ApprovalStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        validate();
    }
    
    private void validate() {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
    }
    
    public void approve() {
        if (this.status != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only PENDING approvals can be approved");
        }
        this.status = ApprovalStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void reject(String reason) {
        if (this.status != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only PENDING approvals can be rejected");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason cannot be empty");
        }
        this.status = ApprovalStatus.REJECTED;
        this.rejectionReason = reason;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isPending() {
        return this.status == ApprovalStatus.PENDING;
    }
    
    public boolean isApproved() {
        return this.status == ApprovalStatus.APPROVED;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public ApprovalStatus getStatus() { return status; }
    public void setStatus(ApprovalStatus status) { this.status = status; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
