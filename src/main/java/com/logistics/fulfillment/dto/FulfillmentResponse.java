package com.logistics.fulfillment.dto;

import com.logistics.fulfillment.entity.FulfillmentStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FulfillmentResponse(
        UUID id,
        UUID orderId,
        String externalFulfillmentId,
        FulfillmentStatus status,
        String carrier,
        String serviceLevel,
        String shipFromLocation,
        OffsetDateTime shippedAt,
        OffsetDateTime deliveredAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
