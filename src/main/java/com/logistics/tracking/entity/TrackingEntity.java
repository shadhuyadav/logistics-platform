package com.logistics.tracking.entity;

import com.logistics.common.entity.BaseAuditEntity;
import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tracking",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tracking_number",
                        columnNames = {"tenant_id", "tracking_number"}
                )
        },
        indexes = {
                @Index(name = "idx_tracking_tenant_fulfillment", columnList = "tenant_id, fulfillment_id"),
                @Index(name = "idx_tracking_tenant_status", columnList = "tenant_id, tracking_status")
        }
)
public class TrackingEntity extends BaseAuditEntity {

    @Id
    @Column(name = "tracking_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "fulfillment_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID fulfillmentId;

    @Column(name = "tracking_number", nullable = false, length = 128)
    private String trackingNumber;

    @Column(name = "tracking_url", length = 1024)
    private String trackingUrl;

    @Column(name = "carrier", length = 64)
    private String carrier;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_status", nullable = false, length = 32)
    private TrackingStatus status = TrackingStatus.UNKNOWN;

    @Column(name = "is_primary", nullable = false)
    private boolean primaryTracking = false;

    @Column(name = "last_event_at")
    private OffsetDateTime lastEventAt;

    protected TrackingEntity() {
    }

    public TrackingEntity(
            UUID id,
            UUID tenantId,
            UUID fulfillmentId,
            String trackingNumber
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.fulfillmentId = fulfillmentId;
        this.trackingNumber = trackingNumber;
    }

    /* ===== getters ===== */

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getFulfillmentId() { return fulfillmentId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getTrackingUrl() { return trackingUrl; }
    public String getCarrier() { return carrier; }
    public TrackingStatus getStatus() { return status; }
    public boolean isPrimaryTracking() { return primaryTracking; }
    public OffsetDateTime getLastEventAt() { return lastEventAt; }

    /* ===== update / patch ===== */

    public void update(
            String trackingUrl,
            String carrier,
            TrackingStatus status,
            Boolean primary,
            OffsetDateTime lastEventAt
    ) {
        this.trackingUrl = trackingUrl;
        this.carrier = carrier;
        if (status != null) this.status = status;
        if (primary != null) this.primaryTracking = primary;
        if (lastEventAt != null) this.lastEventAt = lastEventAt;
    }
}
