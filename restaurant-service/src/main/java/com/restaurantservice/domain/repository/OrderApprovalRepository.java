package com.restaurantservice.domain.repository;

import com.restaurantservice.domain.entity.OrderApproval;
import java.util.List;
import java.util.Optional;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval approval);
    Optional<OrderApproval> findById(Long id);
    Optional<OrderApproval> findByOrderId(Long orderId);
    List<OrderApproval> findAll();
    void deleteById(Long id);
}
