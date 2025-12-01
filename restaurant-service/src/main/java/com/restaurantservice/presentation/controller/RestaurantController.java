package com.restaurantservice.presentation.controller;

import com.restaurantservice.application.dto.OrderApprovalResponse;
import com.restaurantservice.domain.entity.OrderApproval;
import com.restaurantservice.domain.repository.OrderApprovalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    
    private final OrderApprovalRepository approvalRepository;

    public RestaurantController(OrderApprovalRepository approvalRepository) {
        this.approvalRepository = approvalRepository;
    }

    // Order approval history endpoints
    @GetMapping("/approvals")
    public ResponseEntity<List<OrderApprovalResponse>> getAllApprovals() {
        List<OrderApprovalResponse> approvals = approvalRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(approvals);
    }

    @GetMapping("/approvals/order/{orderId}")
    public ResponseEntity<OrderApprovalResponse> getApprovalByOrderId(@PathVariable Long orderId) {
        return approvalRepository.findByOrderId(orderId)
            .map(this::mapToResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    private OrderApprovalResponse mapToResponse(OrderApproval approval) {
        return new OrderApprovalResponse(
            approval.getId(),
            approval.getOrderId(),
            approval.getCustomerId(),
            approval.getStatus().name(),
            approval.getRejectionReason(),
            approval.getCreatedAt(),
            approval.getUpdatedAt()
        );
    }
}
