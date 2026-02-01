package com.logistics.fulfillment.dto;

import com.logistics.fulfillment.entity.FulfillmentStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public record CreateFulfillmentRequest(

        @NotBlank
        String externalFulfillmentId,

        FulfillmentStatus status,
        String carrier,
        String serviceLevel,
        String shipFromLocation,
        OffsetDateTime shippedAt,
        OffsetDateTime deliveredAt
) {
}
