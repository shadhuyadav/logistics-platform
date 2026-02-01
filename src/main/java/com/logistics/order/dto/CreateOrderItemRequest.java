package com.logistics.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderItemRequest(

        String externalLineItemId,
        String sku,
        String title,

        @Min(0)
        Integer quantityOrdered,

        @NotNull
        @Min(0)
        BigDecimal unitPrice
) {
}
