package com.logistics.store.service;

import com.logistics.common.exception.ResourceNotFoundException;
import com.logistics.common.exception.ValidationException;

import com.logistics.store.dto.CreateStoreRequest;
import com.logistics.store.dto.PatchStoreRequest;
import com.logistics.store.dto.StoreResponse;
import com.logistics.store.dto.UpdateStoreRequest;
import com.logistics.store.entity.StoreEntity;
import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;
import com.logistics.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StoreService {

    private final StoreRepository repository;

    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    public StoreResponse create(UUID tenantId, CreateStoreRequest request) {
        if (repository.existsByTenantIdAndCodeIgnoreCase(tenantId, request.code())) {
            throw new ValidationException("Store code already exists for tenant");
        }

        StoreEntity entity = new StoreEntity(
                UUID.randomUUID(),
                tenantId,
                request.code(),
                request.name(),
                request.platform(),
                request.timezone(),
                request.currency(),
                request.status()
        );

        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> list(
            UUID tenantId,
            StoreStatus status,
            StorePlatform platform
    ) {
        List<StoreEntity> stores;

        if (status != null) {
            stores = repository.findByTenantIdAndStatus(tenantId, status);
        } else if (platform != null) {
            stores = repository.findByTenantIdAndPlatform(tenantId, platform);
        } else {
            stores = repository.findByTenantId(tenantId);
        }

        return stores.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public StoreResponse getById(UUID tenantId, UUID storeId) {
        return toResponse(findEntity(tenantId, storeId));
    }

    public StoreResponse update(UUID tenantId, UUID storeId, UpdateStoreRequest request) {
        StoreEntity entity = findEntity(tenantId, storeId);
        entity.update(
                request.code(),
                request.name(),
                request.platform(),
                request.timezone(),
                request.currency(),
                request.status()
        );
        return toResponse(entity);
    }

    public StoreResponse patch(UUID tenantId, UUID storeId, PatchStoreRequest request) {
        StoreEntity entity = findEntity(tenantId, storeId);
        entity.patch(
                request.code(),
                request.name(),
                request.platform(),
                request.timezone(),
                request.currency(),
                request.status()
        );
        return toResponse(entity);
    }

    public void delete(UUID tenantId, UUID storeId) {
        StoreEntity entity = findEntity(tenantId, storeId);
        repository.delete(entity);
    }

    private StoreEntity findEntity(UUID tenantId, UUID storeId) {
        return repository.findByTenantIdAndId(tenantId, storeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Store not found: " + storeId)
                );
    }

    private StoreResponse toResponse(StoreEntity entity) {
        return new StoreResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getCode(),
                entity.getName(),
                entity.getPlatform(),
                entity.getTimezone(),
                entity.getCurrency(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
