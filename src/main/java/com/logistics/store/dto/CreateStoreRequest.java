package com.logistics.store.dto;

import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateStoreRequest(

        @NotBlank
        @Size(min = 2)
        String code,

        @NotBlank
        @Size(min = 2)
        String name,

        StorePlatform platform,
        String timezone,
        String currency,
        StoreStatus status
) {
}
