package com.logistics.order.dto;

import com.logistics.order.entity.FinancialStatus;
import com.logistics.order.entity.FulfillmentStatus;
import com.logistics.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID orgId,
        UUID websiteId,
        String externalOrderId,
        String externalOrderNumber,
        OrderStatus status,
        FinancialStatus financialStatus,
        FulfillmentStatus fulfillmentStatus,
        String customerEmail,
        BigDecimal orderTotal,
        String currency,
        OffsetDateTime orderCreatedAt,
        OffsetDateTime orderUpdatedAt,
        OffsetDateTime ingestedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
