package com.paymentservice.domain;

public class Payment {
    private String orderId;
    private Double amount;

    public Payment(String orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public boolean process() {
        // Giả lập thanh toán thành công luôn
        return true;
    }

    public String getOrderId() { return orderId; }
    public Double getAmount() { return amount; }
}
