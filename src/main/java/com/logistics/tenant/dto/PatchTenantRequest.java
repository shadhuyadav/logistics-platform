package com.logistics.tenant.dto;

import com.logistics.tenant.entity.TenantStatus;
import jakarta.validation.constraints.Size;

public record PatchTenantRequest(

        @Size(min = 2)
        String name,

        TenantStatus status
) {
}
