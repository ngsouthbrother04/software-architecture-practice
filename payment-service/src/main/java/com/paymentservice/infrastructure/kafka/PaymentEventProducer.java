package com.paymentservice.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentDoneEvent(String orderId) {
        kafkaTemplate.send("payment.done", orderId);
        System.out.println("[Payment Service] Gửi sự kiện PAYMENT DONE cho orderId = " + orderId);
    }

    public void sendPaymentFailedEvent(String orderId) {
        kafkaTemplate.send("payment.failed", orderId);
        System.out.println("[Payment Service] Gửi sự kiện PAYMENT FAILED cho orderId = " + orderId);
    }
}