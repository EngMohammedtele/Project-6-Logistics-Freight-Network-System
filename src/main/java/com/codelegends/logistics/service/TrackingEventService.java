package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class TrackingEventService extends CrudService<TrackingEvent, TrackingEventDTO> {
    public TrackingEventService(
            TrackingEventRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, TrackingEvent.class);
    }

    @Override
    protected TrackingEvent newEntity() {
        return new TrackingEvent();
    }

    @Override
    protected TrackingEventDTO toDTO(TrackingEvent entity) {
        return TrackingEventDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(TrackingEventDTO dto, TrackingEvent entity) {
        entity.setEventTime(dto.getEventTime());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
