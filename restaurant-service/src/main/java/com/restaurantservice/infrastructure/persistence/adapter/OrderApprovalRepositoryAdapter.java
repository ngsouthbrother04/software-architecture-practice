package com.restaurantservice.infrastructure.persistence.adapter;

import com.restaurantservice.domain.entity.OrderApproval;
import com.restaurantservice.domain.repository.OrderApprovalRepository;
import com.restaurantservice.infrastructure.persistence.entity.OrderApprovalEntity;
import com.restaurantservice.infrastructure.persistence.repository.JpaOrderApprovalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderApprovalRepositoryAdapter implements OrderApprovalRepository {
    
    private final JpaOrderApprovalRepository jpaRepository;
    
    public OrderApprovalRepositoryAdapter(JpaOrderApprovalRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public OrderApproval save(OrderApproval approval) {
        OrderApprovalEntity entity = toEntity(approval);
        OrderApprovalEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<OrderApproval> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public Optional<OrderApproval> findByOrderId(Long orderId) {
        return jpaRepository.findByOrderId(orderId).map(this::toDomain);
    }
    
    @Override
    public List<OrderApproval> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    private OrderApprovalEntity toEntity(OrderApproval domain) {
        OrderApprovalEntity entity = new OrderApprovalEntity();
        entity.setId(domain.getId());
        entity.setOrderId(domain.getOrderId());
        entity.setCustomerId(domain.getCustomerId());
        entity.setStatus(domain.getStatus());
        entity.setRejectionReason(domain.getRejectionReason());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
    
    private OrderApproval toDomain(OrderApprovalEntity entity) {
        OrderApproval domain = new OrderApproval(entity.getOrderId(), entity.getCustomerId());
        domain.setId(entity.getId());
        domain.setStatus(entity.getStatus());
        domain.setRejectionReason(entity.getRejectionReason());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }
}
