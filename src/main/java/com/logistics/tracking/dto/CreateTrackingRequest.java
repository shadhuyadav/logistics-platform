package com.logistics.tracking.dto;

import com.logistics.tracking.entity.TrackingStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public record CreateTrackingRequest(
        @NotBlank String trackingNumber,
        String carrier,
        String trackingUrl,
        TrackingStatus status,
        Boolean isPrimary,
        OffsetDateTime lastEventAt
) {
}
