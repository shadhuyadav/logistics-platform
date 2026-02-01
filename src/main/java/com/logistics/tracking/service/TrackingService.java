package com.logistics.tracking.service;

import com.logistics.tracking.dto.CreateTrackingEventRequest;
import com.logistics.tracking.dto.CreateTrackingRequest;
import com.logistics.tracking.dto.TrackingResponse;
import com.logistics.tracking.entity.TrackingEntity;
import com.logistics.tracking.entity.TrackingEventEntity;
import com.logistics.tracking.repository.TrackingEventRepository;
import com.logistics.tracking.repository.TrackingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final TrackingEventRepository eventRepository;

    public TrackingService(
            TrackingRepository trackingRepository,
            TrackingEventRepository eventRepository
    ) {
        this.trackingRepository = trackingRepository;
        this.eventRepository = eventRepository;
    }

    public TrackingResponse createTracking(
            UUID tenantId,
            UUID fulfillmentId,
            CreateTrackingRequest request
    ) {
        TrackingEntity entity = trackingRepository
                .findByTenantIdAndTrackingNumber(tenantId, request.trackingNumber())
                .orElseGet(() ->
                        new TrackingEntity(
                                UUID.randomUUID(),
                                tenantId,
                                fulfillmentId,
                                request.trackingNumber()
                        )
                );

        entity.update(
                request.trackingUrl(),
                request.carrier(),
                request.status(),
                request.isPrimary(),
                request.lastEventAt()
        );

        return toResponse(trackingRepository.save(entity));
    }

    public void addEvent(
            UUID tenantId,
            UUID trackingId,
            CreateTrackingEventRequest request
    ) {
        TrackingEventEntity event = new TrackingEventEntity(
                UUID.randomUUID(),
                tenantId,
                trackingId,
                request.eventTime(),
                request.eventCode(),
                request.eventDescription(),
                request.eventCity(),
                request.eventState(),
                request.eventCountry(),
                request.eventZip(),
                request.source(),
                request.eventHash()
        );

        eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<TrackingResponse> list(UUID tenantId, UUID fulfillmentId) {
        return trackingRepository.findByTenantIdAndFulfillmentId(tenantId, fulfillmentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TrackingResponse toResponse(TrackingEntity e) {
        return new TrackingResponse(
                e.getId(),
                e.getFulfillmentId(),
                e.getTrackingNumber(),
                e.getCarrier(),
                e.getTrackingUrl(),
                e.getStatus(),
                e.isPrimaryTracking(),
                e.getLastEventAt(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
