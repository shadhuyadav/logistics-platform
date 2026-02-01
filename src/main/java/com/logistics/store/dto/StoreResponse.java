package com.logistics.store.dto;

import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StoreResponse(
        UUID id,
        UUID orgId,
        String code,
        String name,
        StorePlatform platform,
        String timezone,
        String currency,
        StoreStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
