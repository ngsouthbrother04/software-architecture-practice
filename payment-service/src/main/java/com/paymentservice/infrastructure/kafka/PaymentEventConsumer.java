package com.paymentservice.infrastructure.kafka;

import com.paymentservice.domain.Payment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private final PaymentEventProducer producer;

    public PaymentEventConsumer(PaymentEventProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "order.approved", groupId = "payment-service")
    public void listen(@Payload String message) {
        System.out.println("[Payment Service] Nhận sự kiện ORDER APPROVED: " + message);

        try {
            // Parse JSON message manually or use ObjectMapper
            // Message format example: {"orderId":123, "status":"APPROVED", ...}
            String orderId = "unknown";
            
            if (message.contains("\"orderId\":")) {
                // Simple string parsing to avoid adding Jackson dependency if not present
                // Extract value after "orderId":
                int startIndex = message.indexOf("\"orderId\":") + 10;
                int endIndex = message.indexOf(",", startIndex);
                if (endIndex == -1) endIndex = message.indexOf("}", startIndex);
                
                if (startIndex > 0 && endIndex > startIndex) {
                    orderId = message.substring(startIndex, endIndex).trim();
                }
            } else {
                // Fallback for simple string format if any
                orderId = message.replaceAll("[^0-9]", "");
            }

            Payment payment = new Payment(orderId, 100.0);
            boolean ok = payment.process();

            if (ok) {
                producer.sendPaymentDoneEvent(orderId);
            } else {
                producer.sendPaymentFailedEvent(orderId);
            }
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }
}
