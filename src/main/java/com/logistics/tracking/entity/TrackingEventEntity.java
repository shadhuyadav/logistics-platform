package com.logistics.tracking.entity;

import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tracking_events",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_hash",
                        columnNames = {"tenant_id", "event_hash"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_events_tenant_tracking_time",
                        columnList = "tenant_id, tracking_id, event_time"
                )
        }
)
public class TrackingEventEntity {

    @Id
    @Column(name = "tracking_event_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "tracking_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID trackingId;

    @Column(name = "event_time", nullable = false)
    private OffsetDateTime eventTime;

    @Column(name = "event_code", nullable = false, length = 64)
    private String eventCode;

    @Column(name = "event_description", length = 512)
    private String eventDescription;

    @Column(name = "event_city", length = 128)
    private String eventCity;

    @Column(name = "event_state", length = 128)
    private String eventState;

    @Column(name = "event_country", length = 128)
    private String eventCountry;

    @Column(name = "event_zip", length = 32)
    private String eventZip;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 16)
    private TrackingEventSource source = TrackingEventSource.OTHER;

    @Column(name = "event_hash", columnDefinition = "CHAR(64)", nullable = false)
    private String eventHash;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected TrackingEventEntity() {
    }

    public TrackingEventEntity(
            UUID id,
            UUID tenantId,
            UUID trackingId,
            OffsetDateTime eventTime,
            String eventCode,
            String eventDescription,
            String eventCity,
            String eventState,
            String eventCountry,
            String eventZip,
            TrackingEventSource source,
            String eventHash
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.trackingId = trackingId;
        this.eventTime = eventTime;
        this.eventCode = eventCode;
        this.eventDescription = eventDescription;
        this.eventCity = eventCity;
        this.eventState = eventState;
        this.eventCountry = eventCountry;
        this.eventZip = eventZip;
        this.source = source == null ? TrackingEventSource.OTHER : source;
        this.eventHash = eventHash;
        this.createdAt = OffsetDateTime.now();
    }
}
