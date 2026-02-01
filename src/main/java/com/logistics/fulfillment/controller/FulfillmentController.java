package com.logistics.fulfillment.controller;

import com.logistics.fulfillment.dto.CreateFulfillmentRequest;
import com.logistics.fulfillment.dto.FulfillmentResponse;
import com.logistics.fulfillment.service.FulfillmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders/{orderId}/fulfillments")
public class FulfillmentController {

    private final FulfillmentService service;

    public FulfillmentController(FulfillmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FulfillmentResponse create(
            @PathVariable UUID orderId,
            @RequestParam UUID orgId,
            @Valid @RequestBody CreateFulfillmentRequest request
    ) {
        return service.create(orgId, orderId, request);
    }

    @GetMapping
    public List<FulfillmentResponse> list(
            @PathVariable UUID orderId,
            @RequestParam UUID orgId
    ) {
        return service.list(orgId, orderId);
    }

    @GetMapping("/{fulfillmentId}")
    public FulfillmentResponse getById(
            @PathVariable UUID orderId,
            @PathVariable UUID fulfillmentId,
            @RequestParam UUID orgId
    ) {
        return service.getById(orgId, fulfillmentId);
    }
}
