package com.logistics.store.repository;

import com.logistics.store.entity.StoreEntity;
import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<StoreEntity, UUID> {

    boolean existsByTenantIdAndCodeIgnoreCase(UUID tenantId, String code);

    List<StoreEntity> findByTenantId(UUID tenantId);

    List<StoreEntity> findByTenantIdAndStatus(UUID tenantId, StoreStatus status);

    List<StoreEntity> findByTenantIdAndPlatform(UUID tenantId, StorePlatform platform);

    Optional<StoreEntity> findByTenantIdAndId(UUID tenantId, UUID id);
}
