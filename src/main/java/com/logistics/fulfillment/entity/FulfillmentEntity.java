package com.logistics.fulfillment.entity;

import com.logistics.common.entity.BaseAuditEntity;
import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "fulfillments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_fulfillment_external",
                        columnNames = {"tenant_id", "order_id", "external_fulfillment_id"}
                )
        },
        indexes = {
                @Index(name = "idx_fulfillments_tenant_order", columnList = "tenant_id, order_id"),
                @Index(name = "idx_fulfillments_tenant_updated", columnList = "tenant_id, updated_at")
        }
)
public class FulfillmentEntity extends BaseAuditEntity {

    @Id
    @Column(name = "fulfillment_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "order_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID orderId;

    @Column(name = "external_fulfillment_id", nullable = false, length = 128)
    private String externalFulfillmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "fulfillment_status", nullable = false, length = 32)
    private FulfillmentStatus status = FulfillmentStatus.UNKNOWN;

    @Column(name = "carrier", length = 64)
    private String carrier;

    @Column(name = "service_level", length = 64)
    private String serviceLevel;

    @Column(name = "ship_from_location", length = 255)
    private String shipFromLocation;

    @Column(name = "shipped_at")
    private OffsetDateTime shippedAt;

    @Column(name = "delivered_at")
    private OffsetDateTime deliveredAt;

    @Column(name = "raw_payload_json", columnDefinition = "json")
    private String rawPayloadJson;

    protected FulfillmentEntity() {
    }

    public FulfillmentEntity(
            UUID id,
            UUID tenantId,
            UUID orderId,
            String externalFulfillmentId
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.orderId = orderId;
        this.externalFulfillmentId = externalFulfillmentId;
    }



    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getOrderId() { return orderId; }
    public String getExternalFulfillmentId() { return externalFulfillmentId; }
    public FulfillmentStatus getStatus() { return status; }
    public String getCarrier() { return carrier; }
    public String getServiceLevel() { return serviceLevel; }
    public String getShipFromLocation() { return shipFromLocation; }
    public OffsetDateTime getShippedAt() { return shippedAt; }
    public OffsetDateTime getDeliveredAt() { return deliveredAt; }



    public void update(
            FulfillmentStatus status,
            String carrier,
            String serviceLevel,
            String shipFromLocation,
            OffsetDateTime shippedAt,
            OffsetDateTime deliveredAt,
            String rawPayloadJson
    ) {
        if (status != null) this.status = status;
        this.carrier = carrier;
        this.serviceLevel = serviceLevel;
        this.shipFromLocation = shipFromLocation;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
        this.rawPayloadJson = rawPayloadJson;
    }

    public void patch(
            FulfillmentStatus status,
            String carrier,
            String serviceLevel,
            String shipFromLocation,
            OffsetDateTime shippedAt,
            OffsetDateTime deliveredAt
    ) {
        if (status != null) this.status = status;
        if (carrier != null) this.carrier = carrier;
        if (serviceLevel != null) this.serviceLevel = serviceLevel;
        if (shipFromLocation != null) this.shipFromLocation = shipFromLocation;
        if (shippedAt != null) this.shippedAt = shippedAt;
        if (deliveredAt != null) this.deliveredAt = deliveredAt;
    }
}
