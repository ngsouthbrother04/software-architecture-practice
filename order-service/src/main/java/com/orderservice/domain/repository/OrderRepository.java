package com.orderservice.domain.repository;

import com.orderservice.domain.entity.Order;
import java.util.Optional;
import java.util.List;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);
}
