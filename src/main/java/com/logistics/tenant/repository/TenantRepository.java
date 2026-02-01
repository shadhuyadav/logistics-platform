package com.logistics.tenant.repository;

import com.logistics.tenant.entity.TenantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TenantRepository
        extends JpaRepository<TenantEntity, UUID>,
        JpaSpecificationExecutor<TenantEntity> {
    Page<TenantEntity> findByExternalId(String externalId, Pageable pageable);
}
