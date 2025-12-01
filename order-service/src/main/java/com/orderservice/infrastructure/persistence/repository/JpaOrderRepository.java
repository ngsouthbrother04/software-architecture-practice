package com.orderservice.infrastructure.persistence.repository;

import com.orderservice.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
    // Spring Data JPA tự động implement các method cơ bản
}
