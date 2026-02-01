package com.logistics.store.controller;

import com.logistics.store.dto.CreateStoreRequest;
import com.logistics.store.dto.PatchStoreRequest;
import com.logistics.store.dto.StoreResponse;
import com.logistics.store.dto.UpdateStoreRequest;
import com.logistics.store.entity.StorePlatform;
import com.logistics.store.entity.StoreStatus;
import com.logistics.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations/{orgId}/websites")
public class StoreController {

    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreResponse create(
            @PathVariable UUID orgId,
            @Valid @RequestBody CreateStoreRequest request
    ) {
        return service.create(orgId, request);
    }

    @GetMapping
    public List<StoreResponse> list(
            @PathVariable UUID orgId,
            @RequestParam(required = false) StoreStatus status,
            @RequestParam(required = false) StorePlatform platform
    ) {
        return service.list(orgId, status, platform);
    }

    @GetMapping("/{websiteId}")
    public StoreResponse getById(
            @PathVariable UUID orgId,
            @PathVariable UUID websiteId
    ) {
        return service.getById(orgId, websiteId);
    }

    @PutMapping("/{websiteId}")
    public StoreResponse update(
            @PathVariable UUID orgId,
            @PathVariable UUID websiteId,
            @Valid @RequestBody UpdateStoreRequest request
    ) {
        return service.update(orgId, websiteId, request);
    }

    @PatchMapping("/{websiteId}")
    public StoreResponse patch(
            @PathVariable UUID orgId,
            @PathVariable UUID websiteId,
            @Valid @RequestBody PatchStoreRequest request
    ) {
        return service.patch(orgId, websiteId, request);
    }

    @DeleteMapping("/{websiteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID orgId,
            @PathVariable UUID websiteId
    ) {
        service.delete(orgId, websiteId);
    }
}
