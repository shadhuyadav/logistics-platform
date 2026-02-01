package com.logistics.tracking.repository;

import com.logistics.tracking.entity.TrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrackingRepository extends JpaRepository<TrackingEntity, UUID> {

    List<TrackingEntity> findByTenantIdAndFulfillmentId(UUID tenantId, UUID fulfillmentId);

    Optional<TrackingEntity> findByTenantIdAndTrackingNumber(UUID tenantId, String trackingNumber);

    Optional<TrackingEntity> findByTenantIdAndId(UUID tenantId, UUID trackingId);
}
