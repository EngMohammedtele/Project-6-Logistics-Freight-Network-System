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
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, TrackingEvent.class);
    }

    @Override
    /** Creates a new TrackingEvent entity instance for create requests. */
    protected TrackingEvent newEntity() {
        // Returns a blank TrackingEvent instance that the copy method will populate.
        return new TrackingEvent();
    }

    @Override
    /** Converts the persisted TrackingEvent entity to its DTO representation. */
    protected TrackingEventDTO toDTO(TrackingEvent entity) {
        // Reuses the DTO mapper to expose persisted tracking event values to callers.
        return TrackingEventDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the TrackingEvent entity. */
    // Maps request DTO fields onto the mutable TrackingEvent entity.
    protected void copy(TrackingEventDTO dto, TrackingEvent entity) {
        // Copies when the tracking update occurred.
        entity.setEventTime(dto.getEventTime());
        // Copies the tracking location reported by the event.
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
