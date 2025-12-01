package com.orderservice.application.dto;

import com.orderservice.domain.valueobject.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private String customerId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    
    // Constructor
    public OrderResponse(Long id, String customerId, BigDecimal totalAmount, 
                        OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
