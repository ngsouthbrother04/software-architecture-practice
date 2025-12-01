package com.orderservice.domain.entity;

import com.orderservice.domain.valueobject.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long id;
    private String customerId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    
    // Constructor
    public Order(String customerId, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        validateOrder();
    }
    
    // Domain logic - Self validation
    public void validateOrder() {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
    }
    
    // Business methods
    public void approve() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be approved");
        }
        this.status = OrderStatus.APPROVED;
    }
    
    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalStateException("Cannot cancel paid orders");
        }
        this.status = OrderStatus.CANCELLED;
    }
    
    public void markAsPaid() {
        if (this.status != OrderStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED orders can be paid");
        }
        this.status = OrderStatus.PAID;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
