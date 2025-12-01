package com.orderservice.application.dto;

public class OrderApprovedEvent {
    private Long orderId;
    private String customerId;
    private String status;
    private String approvedAt;

    public OrderApprovedEvent() {}

    public OrderApprovedEvent(Long orderId, String customerId, String status, String approvedAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.approvedAt = approvedAt;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getApprovedAt() { return approvedAt; }
    public void setApprovedAt(String approvedAt) { this.approvedAt = approvedAt; }
}
