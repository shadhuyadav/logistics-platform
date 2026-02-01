package com.logistics.order.repository;

import com.logistics.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByTenantIdAndStoreIdAndExternalOrderId(
            UUID tenantId,
            UUID storeId,
            String externalOrderId
    );
}
