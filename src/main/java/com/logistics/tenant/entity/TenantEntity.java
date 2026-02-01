package com.logistics.tenant.entity;

import com.logistics.common.entity.BaseAuditEntity;
import com.logistics.common.util.UuidBinaryConverter;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "tenant",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tenant_name", columnNames = "tenant_name"),
                @UniqueConstraint(name = "uk_tenant_external_id", columnNames = "external_id")
        }
)
public class TenantEntity extends BaseAuditEntity {

    @Id
    @Column(name = "tenant_id", nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UuidBinaryConverter.class)
    private UUID id;

    @Column(name = "external_id", length = 128)
    private String externalId;

    @Column(name = "tenant_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private TenantStatus status = TenantStatus.ACTIVE;

    protected TenantEntity() {
        // JPA only
    }

    public TenantEntity(UUID id, String name, String externalId, TenantStatus status) {
        this.id = id;
        this.name = name;
        this.externalId = externalId;
        this.status = status == null ? TenantStatus.ACTIVE : status;
    }

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public TenantStatus getStatus() {
        return status;
    }


    public void update(String name, TenantStatus status) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tenant name is mandatory");
        }
        if (status == null) {
            throw new IllegalArgumentException("Tenant status is mandatory");
        }
        this.name = name;
        this.status = status;
    }


    public void patch(String name, TenantStatus status) {
        if (name != null) {
            this.name = name;
        }
        if (status != null) {
            this.status = status;
        }
    }
}
