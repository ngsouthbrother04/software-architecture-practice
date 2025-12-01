package com.orderservice.presentation.controller;

import com.orderservice.application.dto.CreateOrderRequest;
import com.orderservice.application.dto.OrderResponse;
import com.orderservice.application.port.input.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final CreateOrderUseCase createOrderUseCase;
    
    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }
    
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        
        log.info("Received create order request for customer: {}", 
                 request.getCustomerId());
        
        OrderResponse response = createOrderUseCase.createOrder(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        log.info("Received get order request for ID: {}", id);
        
        OrderResponse response = createOrderUseCase.getOrderById(id);
        
        return ResponseEntity.ok(response);
    }
}