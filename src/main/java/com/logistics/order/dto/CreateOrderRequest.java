package com.logistics.order.dto;

import com.logistics.order.entity.FinancialStatus;
import com.logistics.order.entity.FulfillmentStatus;
import com.logistics.order.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull UUID orgId,
        @NotNull UUID websiteId,
        @NotBlank String externalOrderId,

        String externalOrderNumber,
        OrderStatus status,
        FinancialStatus financialStatus,
        FulfillmentStatus fulfillmentStatus,
        String customerEmail,
        BigDecimal orderTotal,
        String currency,
        OffsetDateTime orderCreatedAt,
        OffsetDateTime orderUpdatedAt
) {
}
