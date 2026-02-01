package com.logistics.order.entity;

import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_order_external",
                        columnNames = {"tenant_id", "store_id", "external_order_id"}
                )
        }
)
public class OrderEntity {

    @Id
    @Column(name = "order_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "store_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID storeId;

    @Column(name = "external_order_id", nullable = false, length = 128)
    private String externalOrderId;

    @Column(name = "external_order_number", length = 128)
    private String externalOrderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 16)
    private OrderStatus status = OrderStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "financial_status", nullable = false, length = 32)
    private FinancialStatus financialStatus = FinancialStatus.UNKNOWN;

    @Enumerated(EnumType.STRING)
    @Column(name = "fulfillment_status", nullable = false, length = 32)
    private FulfillmentStatus fulfillmentStatus = FulfillmentStatus.UNKNOWN;

    @Column(name = "customer_email", length = 320)
    private String customerEmail;

    @Column(name = "order_total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderTotalAmount = BigDecimal.ZERO;

    @Column(name = "currency", columnDefinition = "CHAR(3)")
    private String currency;

    @Column(name = "order_created_at")
    private OffsetDateTime orderCreatedAt;

    @Column(name = "order_updated_at")
    private OffsetDateTime orderUpdatedAt;

    @Column(name = "ingested_at", nullable = false)
    private OffsetDateTime ingestedAt;

    @Column(name = "raw_payload_json", columnDefinition = "json")
    private String rawPayloadJson;

    protected OrderEntity() {
    }

    public OrderEntity(UUID id, UUID tenantId, UUID storeId, String externalOrderId) {
        this.id = id;
        this.tenantId = tenantId;
        this.storeId = storeId;
        this.externalOrderId = externalOrderId;
        this.ingestedAt = OffsetDateTime.now();
    }



    public UUID getId() {
        return id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public String getExternalOrderNumber() {
        return externalOrderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public FulfillmentStatus getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public OffsetDateTime getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public OffsetDateTime getOrderUpdatedAt() {
        return orderUpdatedAt;
    }

    public OffsetDateTime getIngestedAt() {
        return ingestedAt;
    }



    public void upsertUpdate(
            String externalOrderNumber,
            OrderStatus status,
            FinancialStatus financialStatus,
            FulfillmentStatus fulfillmentStatus,
            String customerEmail,
            BigDecimal orderTotalAmount,
            String currency,
            OffsetDateTime orderCreatedAt,
            OffsetDateTime orderUpdatedAt,
            String rawPayloadJson
    ) {
        this.externalOrderNumber = externalOrderNumber;

        if (status != null) {
            this.status = status;
        }
        if (financialStatus != null) {
            this.financialStatus = financialStatus;
        }
        if (fulfillmentStatus != null) {
            this.fulfillmentStatus = fulfillmentStatus;
        }

        this.customerEmail = customerEmail;

        if (orderTotalAmount != null) {
            this.orderTotalAmount = orderTotalAmount;
        }

        this.currency = currency;


        if (this.orderCreatedAt == null) {
            this.orderCreatedAt = orderCreatedAt;
        }


        if (orderUpdatedAt != null &&
                (this.orderUpdatedAt == null || orderUpdatedAt.isAfter(this.orderUpdatedAt))) {
            this.orderUpdatedAt = orderUpdatedAt;
        }

        this.rawPayloadJson = rawPayloadJson;
        this.ingestedAt = OffsetDateTime.now();
    }
}
