package com.logistics.tenant.controller;

import com.logistics.common.dto.PagedResponse;
import com.logistics.tenant.dto.CreateTenantRequest;
import com.logistics.tenant.dto.PatchTenantRequest;
import com.logistics.tenant.dto.TenantResponse;
import com.logistics.tenant.dto.UpdateTenantRequest;
import com.logistics.tenant.entity.TenantStatus;
import com.logistics.tenant.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public TenantResponse create(@RequestBody @Valid CreateTenantRequest request) {
        return tenantService.create(request);
    }

    @GetMapping("/{id}")
    public TenantResponse getById(@PathVariable UUID id) {
        return tenantService.getById(id);
    }

    @GetMapping("/search")
    public Page<TenantResponse> searchByExternalId(
            @RequestParam String externalId,
            Pageable pageable
    ) {
        if (externalId == null || externalId.isBlank()) {
            throw new IllegalArgumentException("externalId is required");
        }
        return tenantService.searchByExternalId(externalId, pageable);
    }

    @GetMapping
    public PagedResponse<TenantResponse> list(
            @RequestParam(required = false) TenantStatus status,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Instant from,
            @RequestParam(required = false) Instant to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return PagedResponse.from(
                tenantService.list(status, name, from, to, pageable)
        );
    }

    @PutMapping("/{id}")
    public TenantResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTenantRequest request
    ) {
        return tenantService.update(id, request);
    }

    @PatchMapping("/{id}")
    public TenantResponse patch(
            @PathVariable UUID id,
            @RequestBody PatchTenantRequest request
    ) {
        return tenantService.patch(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        tenantService.delete(id);
    }

    private Sort parseSort(String sort) {
        String[] parts = sort.split(",");
        return Sort.by(
                parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                parts[0]
        );
    }
}
