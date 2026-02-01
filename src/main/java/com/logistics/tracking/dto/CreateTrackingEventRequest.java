package com.logistics.tracking.dto;

import com.logistics.tracking.entity.TrackingEventSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record CreateTrackingEventRequest(

        @NotNull OffsetDateTime eventTime,
        @NotBlank String eventCode,
        String eventDescription,
        String eventCity,
        String eventState,
        String eventCountry,
        String eventZip,
        TrackingEventSource source,

        @NotBlank String eventHash
) {
}
