package com.logistics.tracking.dto;

import com.logistics.tracking.entity.TrackingStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TrackingResponse(
        UUID id,
        UUID fulfillmentId,
        String trackingNumber,
        String carrier,
        String trackingUrl,
        TrackingStatus status,
        boolean isPrimary,
        OffsetDateTime lastEventAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
