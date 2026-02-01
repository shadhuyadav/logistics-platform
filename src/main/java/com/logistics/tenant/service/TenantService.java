package com.logistics.tenant.service;

import com.logistics.common.exception.DataIntegrityViolationException;
import com.logistics.common.exception.ResourceNotFoundException;
import com.logistics.tenant.dto.CreateTenantRequest;
import com.logistics.tenant.dto.PatchTenantRequest;
import com.logistics.tenant.dto.TenantResponse;
import com.logistics.tenant.dto.UpdateTenantRequest;
import com.logistics.tenant.entity.TenantEntity;
import com.logistics.tenant.entity.TenantStatus;
import com.logistics.tenant.repository.TenantRepository;
import com.logistics.tenant.repository.TenantSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class TenantService {

    private final TenantRepository repository;

    public TenantService(TenantRepository repository) {
        this.repository = repository;
    }



    public TenantResponse create(CreateTenantRequest request) {
        try {
            TenantEntity tenant = new TenantEntity(
                    UUID.randomUUID(),
                    request.name(),
                    request.externalId(),
                    request.status()
            );
            return TenantResponse.fromEntity(repository.saveAndFlush(tenant));
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new com.logistics.common.exception.DataIntegrityViolationException(
                    "Tenant with same name or externalId already exists"
            );
        }
    }




    @Transactional(readOnly = true)
    public TenantResponse getById(UUID id) {
        return repository.findById(id)
                .map(TenantResponse::fromEntity)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found: " + id)
                );
    }


    @Transactional(readOnly = true)
    public Page<TenantResponse> searchByExternalId(String externalId, Pageable pageable) {
        return repository.findByExternalId(externalId, pageable)
                .map(TenantResponse::fromEntity);
    }



    @Transactional(readOnly = true)
    public Page<TenantResponse> list(
            TenantStatus status,
            String name,
            Instant from,
            Instant to,
            Pageable pageable
    ) {
        Specification<TenantEntity> spec =
                Specification.where(TenantSpecifications.statusEquals(status))
                        .and(TenantSpecifications.nameContains(name))
                        .and(TenantSpecifications.createdFrom(from))
                        .and(TenantSpecifications.createdTo(to));

        return repository.findAll(spec, pageable)
                .map(TenantResponse::fromEntity);
    }



    public TenantResponse update(UUID id, UpdateTenantRequest request) {
        TenantEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found: " + id)
                );

        entity.update(request.name(), request.status());

        try {
            return TenantResponse.fromEntity(repository.save(entity));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Tenant name already exists");
        }
    }



    public TenantResponse patch(UUID id, PatchTenantRequest request) {
        TenantEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found: " + id)
                );

        entity.patch(request.name(), request.status());
        return TenantResponse.fromEntity(repository.save(entity));
    }



    public void delete(UUID id) {
        TenantEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found: " + id)
                );
        repository.delete(entity);
    }
}
