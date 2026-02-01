package com.logistics.store.entity;

import com.logistics.common.entity.BaseAuditEntity;
import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "store",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_store_code_per_tenant",
                        columnNames = {"tenant_id", "store_code"}
                )
        },
        indexes = {
                @Index(name = "idx_store_tenant", columnList = "tenant_id")
        }
)
public class StoreEntity extends BaseAuditEntity {

    @Id
    @Column(name = "store_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID tenantId;

    @Column(name = "store_code", nullable = false, length = 100)
    private String code;

    @Column(name = "store_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false, length = 16)
    private StorePlatform platform = StorePlatform.OTHER;

    @Column(name = "timezone", length = 64)
    private String timezone;

    @Column(name = "currency", columnDefinition = "CHAR(3)")
    private String currency;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private StoreStatus status = StoreStatus.ACTIVE;

    protected StoreEntity() {
    }

    public StoreEntity(
            UUID id,
            UUID tenantId,
            String code,
            String name,
            StorePlatform platform,
            String timezone,
            String currency,
            StoreStatus status
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.code = code;
        this.name = name;
        this.platform = platform == null ? StorePlatform.OTHER : platform;
        this.timezone = timezone;
        this.currency = currency;
        this.status = status == null ? StoreStatus.ACTIVE : status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public StorePlatform getPlatform() {
        return platform;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public StoreStatus getStatus() {
        return status;
    }

    public void update(
            String code,
            String name,
            StorePlatform platform,
            String timezone,
            String currency,
            StoreStatus status
    ) {
        this.code = code;
        this.name = name;
        this.platform = platform;
        this.timezone = timezone;
        this.currency = currency;
        this.status = status;
    }

    public void patch(
            String code,
            String name,
            StorePlatform platform,
            String timezone,
            String currency,
            StoreStatus status
    ) {
        if (code != null) this.code = code;
        if (name != null) this.name = name;
        if (platform != null) this.platform = platform;
        if (timezone != null) this.timezone = timezone;
        if (currency != null) this.currency = currency;
        if (status != null) this.status = status;
    }
}
