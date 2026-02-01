package com.logistics.order.repository;

import com.logistics.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    List<OrderItemEntity> findByTenantIdAndOrderId(UUID tenantId, UUID orderId);

    Optional<OrderItemEntity> findByTenantIdAndOrderIdAndExternalLineItemId(
            UUID tenantId,
            UUID orderId,
            String externalLineItemId
    );
}
