package com.logistics.tenant.dto;

import com.logistics.tenant.entity.TenantEntity;
import com.logistics.tenant.entity.TenantStatus;

import java.time.Instant;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        TenantStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static TenantResponse fromEntity(TenantEntity entity) {
        return new TenantResponse(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getCreatedAt().toInstant(),
                entity.getUpdatedAt().toInstant()
        );
    }
}
