package com.logistics.tenant.dto;

import com.logistics.tenant.entity.TenantStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTenantRequest(

        @NotBlank
        @Size(min = 2)
        String name,
        String externalId,
        TenantStatus status
) {
}
