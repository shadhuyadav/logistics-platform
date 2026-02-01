package com.logistics.tracking.repository;

import com.logistics.tracking.entity.TrackingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackingEventRepository extends JpaRepository<TrackingEventEntity, UUID> {
}
