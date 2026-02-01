package com.logistics.store.dto;

import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateStoreRequest(

        @NotBlank
        @Size(min = 2)
        String code,

        @NotBlank
        @Size(min = 2)
        String name,

        @NotNull
        StorePlatform platform,

        String timezone,
        String currency,

        @NotNull
        StoreStatus status
) {
}
