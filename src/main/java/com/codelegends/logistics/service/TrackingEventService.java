package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for tracking event resources.
 */
// Registers this class as the Spring service for tracking event workflows.
@Service
// Specializes the shared CRUD workflow for TrackingEvent entities and DTOs.
public class TrackingEventService extends CrudService<TrackingEvent, TrackingEventDTO> {
    /** Injects persistence access and shared rule validation for TrackingEvent resources. */
    public TrackingEventService(
            TrackingEventRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, TrackingEvent.class);
    }

    @Override
    /** Creates a new TrackingEvent entity instance for create requests. */
    protected TrackingEvent newEntity() {
        return new TrackingEvent();
    }

    @Override
    /** Converts the persisted TrackingEvent entity to its DTO representation. */
    protected TrackingEventDTO toDTO(TrackingEvent entity) {
        return TrackingEventDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the TrackingEvent entity. */
    protected void copy(TrackingEventDTO dto, TrackingEvent entity) {
        entity.setEventTime(dto.getEventTime());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
