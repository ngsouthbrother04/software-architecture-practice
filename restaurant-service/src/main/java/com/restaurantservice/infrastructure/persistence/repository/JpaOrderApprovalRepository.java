package com.restaurantservice.infrastructure.persistence.repository;

import com.restaurantservice.infrastructure.persistence.entity.OrderApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaOrderApprovalRepository extends JpaRepository<OrderApprovalEntity, Long> {
    Optional<OrderApprovalEntity> findByOrderId(Long orderId);
}
