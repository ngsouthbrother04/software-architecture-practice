package com.orderservice.infrastructure.persistence.entity;

import com.orderservice.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_customer_id", columnList = "customer_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id", nullable = false, length = 100)
    private String customerId;
    
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    // Constructors
    public OrderEntity() {}
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(String customerId) { 
        this.customerId = customerId; 
    }
    
    public BigDecimal getTotalAmount() { 
        return totalAmount; 
    }
    
    public void setTotalAmount(BigDecimal totalAmount) { 
        this.totalAmount = totalAmount; 
    }
    
    public OrderStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(OrderStatus status) { 
        this.status = status; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
