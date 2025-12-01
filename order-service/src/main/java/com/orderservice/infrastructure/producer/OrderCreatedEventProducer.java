package com.orderservice.infrastructure.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedEventProducer.class);
    private static final String TOPIC = "order.created";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderCreatedEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(Long orderId) {
        String message = String.format("{\"orderId\":%d}", orderId); // JSON simple
        log.info("Publishing order.created event: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }
}
