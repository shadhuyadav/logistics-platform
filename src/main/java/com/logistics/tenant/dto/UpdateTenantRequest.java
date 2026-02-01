package com.logistics.tenant.dto;

import com.logistics.tenant.entity.TenantStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTenantRequest(

        @NotBlank
        @Size(min = 2)
        String name,

        @NotNull
        TenantStatus status
) {
}
