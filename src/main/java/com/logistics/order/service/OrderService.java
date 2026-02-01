package com.logistics.order.service;

import com.logistics.common.exception.ResourceNotFoundException;
import com.logistics.order.dto.CreateOrderRequest;
import com.logistics.order.dto.OrderResponse;
import com.logistics.order.entity.OrderEntity;
import com.logistics.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }


    public OrderResponse createOrUpsert(CreateOrderRequest request) {

        OrderEntity order = repository
                .findByTenantIdAndStoreIdAndExternalOrderId(
                        request.orgId(),
                        request.websiteId(),
                        request.externalOrderId()
                )
                .orElseGet(() ->
                        new OrderEntity(
                                UUID.randomUUID(),
                                request.orgId(),
                                request.websiteId(),
                                request.externalOrderId()
                        )
                );

        order.upsertUpdate(
                request.externalOrderNumber(),
                request.status(),
                request.financialStatus(),
                request.fulfillmentStatus(),
                request.customerEmail(),
                request.orderTotal(),
                request.currency(),
                request.orderCreatedAt(),
                request.orderUpdatedAt(),
                null   // raw_payload_json (future use)
        );

        return toResponse(repository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(UUID orderId) {
        OrderEntity order = repository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found: " + orderId)
                );

        return toResponse(order);
    }



    private OrderResponse toResponse(OrderEntity o) {
        return new OrderResponse(
                o.getId(),
                o.getTenantId(),
                o.getStoreId(),
                o.getExternalOrderId(),
                o.getExternalOrderNumber(),
                o.getStatus(),
                o.getFinancialStatus(),
                o.getFulfillmentStatus(),
                o.getCustomerEmail(),
                o.getOrderTotalAmount(),
                o.getCurrency(),
                o.getOrderCreatedAt(),
                o.getOrderUpdatedAt(),
                o.getIngestedAt(),
                null,   // createdAt (NOT APPLICABLE for orders)
                null    // updatedAt (NOT APPLICABLE for orders)
        );
    }
}
