package com.logistics.order.entity;

import com.logistics.common.entity.BaseAuditEntity;
import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "order_items",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_order_line",
                        columnNames = {"tenant_id", "order_id", "external_line_item_id"}
                )
        },
        indexes = {
                @Index(name = "idx_items_tenant_order", columnList = "tenant_id, order_id"),
                @Index(name = "idx_items_sku", columnList = "tenant_id, sku")
        }
)
public class OrderItemEntity extends BaseAuditEntity {

    @Id
    @Column(name = "order_item_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "order_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID orderId;

    @Column(name = "external_line_item_id", length = 128)
    private String externalLineItemId;

    @Column(name = "sku", length = 128)
    private String sku;

    @Column(name = "title", length = 512)
    private String title;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered = 0;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    protected OrderItemEntity() {
    }

    public OrderItemEntity(
            UUID id,
            UUID tenantId,
            UUID orderId,
            String externalLineItemId,
            String sku,
            String title,
            Integer quantityOrdered,
            BigDecimal unitPrice
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.orderId = orderId;
        this.externalLineItemId = externalLineItemId;
        this.sku = sku;
        this.title = title;
        this.quantityOrdered = quantityOrdered == null ? 0 : quantityOrdered;
        this.unitPrice = unitPrice == null ? BigDecimal.ZERO : unitPrice;
    }



    public UUID getId() {
        return id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getExternalLineItemId() {
        return externalLineItemId;
    }

    public String getSku() {
        return sku;
    }

    public String getTitle() {
        return title;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}