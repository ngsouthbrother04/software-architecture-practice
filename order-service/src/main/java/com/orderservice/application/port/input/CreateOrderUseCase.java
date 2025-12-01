package com.orderservice.application.port.input;

import com.orderservice.application.dto.CreateOrderRequest;
import com.orderservice.application.dto.OrderResponse;

public interface CreateOrderUseCase {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);
}
