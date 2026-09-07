package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for delivery stop resources.
 */
// Registers this class as the Spring service for delivery stop workflows.
@Service
// Specializes the shared CRUD workflow for DeliveryStop entities and DTOs.
public class DeliveryStopService extends CrudService<DeliveryStop, DeliveryStopDTO> {
    /** Injects persistence access and shared rule validation for DeliveryStop resources. */
    public DeliveryStopService(
            DeliveryStopRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, DeliveryStop.class);
    }

    @Override
    /** Creates a new DeliveryStop entity instance for create requests. */
    protected DeliveryStop newEntity() {
        return new DeliveryStop();
    }

    @Override
    /** Converts the persisted DeliveryStop entity to its DTO representation. */
    protected DeliveryStopDTO toDTO(DeliveryStop entity) {
        return DeliveryStopDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the DeliveryStop entity. */
    protected void copy(DeliveryStopDTO dto, DeliveryStop entity) {
        entity.setSequence(dto.getSequence());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setEta(dto.getEta());
        entity.setRoute(access.get(Route.class, dto.getRouteId()));
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
