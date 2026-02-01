package com.logistics.tracking.controller;

import com.logistics.tracking.dto.CreateTrackingEventRequest;
import com.logistics.tracking.dto.CreateTrackingRequest;
import com.logistics.tracking.dto.TrackingResponse;
import com.logistics.tracking.service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fulfillments/{fulfillmentId}/tracking")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackingResponse create(
            @PathVariable UUID fulfillmentId,
            @RequestParam UUID orgId,
            @Valid @RequestBody CreateTrackingRequest request
    ) {
        return service.createTracking(orgId, fulfillmentId, request);
    }

    @GetMapping
    public List<TrackingResponse> list(
            @PathVariable UUID fulfillmentId,
            @RequestParam UUID orgId
    ) {
        return service.list(orgId, fulfillmentId);
    }

    @PostMapping("/{trackingId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(
            @PathVariable UUID trackingId,
            @RequestParam UUID orgId,
            @Valid @RequestBody CreateTrackingEventRequest request
    ) {
        service.addEvent(orgId, trackingId, request);
    }
}
