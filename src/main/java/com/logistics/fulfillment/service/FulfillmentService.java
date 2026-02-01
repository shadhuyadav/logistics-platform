package com.logistics.fulfillment.service;

import com.logistics.common.exception.ResourceNotFoundException;
import com.logistics.fulfillment.dto.CreateFulfillmentRequest;
import com.logistics.fulfillment.dto.FulfillmentResponse;
import com.logistics.fulfillment.entity.FulfillmentEntity;
import com.logistics.fulfillment.repository.FulfillmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FulfillmentService {

    private final FulfillmentRepository repository;

    public FulfillmentService(FulfillmentRepository repository) {
        this.repository = repository;
    }

    public FulfillmentResponse create(
            UUID tenantId,
            UUID orderId,
            CreateFulfillmentRequest request
    ) {
        FulfillmentEntity entity = repository
                .findByTenantIdAndOrderIdAndExternalFulfillmentId(
                        tenantId,
                        orderId,
                        request.externalFulfillmentId()
                )
                .orElseGet(() ->
                        new FulfillmentEntity(
                                UUID.randomUUID(),
                                tenantId,
                                orderId,
                                request.externalFulfillmentId()
                        )
                );

        entity.update(
                request.status(),
                request.carrier(),
                request.serviceLevel(),
                request.shipFromLocation(),
                request.shippedAt(),
                request.deliveredAt(),
                null
        );

        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<FulfillmentResponse> list(UUID tenantId, UUID orderId) {
        return repository.findByTenantIdAndOrderId(tenantId, orderId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FulfillmentResponse getById(
            UUID tenantId,
            UUID fulfillmentId
    ) {
        return toResponse(
                repository.findByTenantIdAndId(tenantId, fulfillmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Fulfillment not found: " + fulfillmentId)
                        )
        );
    }

    private FulfillmentResponse toResponse(FulfillmentEntity e) {
        return new FulfillmentResponse(
                e.getId(),
                e.getOrderId(),
                e.getExternalFulfillmentId(),
                e.getStatus(),
                e.getCarrier(),
                e.getServiceLevel(),
                e.getShipFromLocation(),
                e.getShippedAt(),
                e.getDeliveredAt(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
