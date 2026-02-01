package com.logistics.fulfillment.repository;

import com.logistics.fulfillment.entity.FulfillmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FulfillmentRepository extends JpaRepository<FulfillmentEntity, UUID> {

    List<FulfillmentEntity> findByTenantIdAndOrderId(UUID tenantId, UUID orderId);

    Optional<FulfillmentEntity> findByTenantIdAndOrderIdAndExternalFulfillmentId(
            UUID tenantId,
            UUID orderId,
            String externalFulfillmentId
    );

    Optional<FulfillmentEntity> findByTenantIdAndId(UUID tenantId, UUID fulfillmentId);
}
