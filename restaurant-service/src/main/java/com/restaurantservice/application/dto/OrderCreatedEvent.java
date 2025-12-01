package com.restaurantservice.application.dto;

import java.math.BigDecimal;

public class OrderCreatedEvent {
    private Long orderId;
    private String customerId;
    private BigDecimal totalAmount;
    private String status;
    private String createdAt;
    
    public OrderCreatedEvent() {}
    
    public OrderCreatedEvent(Long orderId, String customerId, BigDecimal totalAmount, String status, String createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
