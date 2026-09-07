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
        // Returns a blank DeliveryStop instance that the copy method will populate.
        return new DeliveryStop();
    }

    @Override
    /** Converts the persisted DeliveryStop entity to its DTO representation. */
    protected DeliveryStopDTO toDTO(DeliveryStop entity) {
        // Reuses the DTO mapper to expose persisted delivery stop values to callers.
        return DeliveryStopDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the DeliveryStop entity. */
    // Maps request DTO fields onto the mutable DeliveryStop entity.
    protected void copy(DeliveryStopDTO dto, DeliveryStop entity) {
        // Copies the stop sequence used to order route deliveries.
        entity.setSequence(dto.getSequence());
        // Copies the delivery address from the stop DTO.
        entity.setAddress(dto.getAddress());
        // Copies the stop status supplied by validated service input.
        entity.setStatus(dto.getStatus());
        entity.setEta(dto.getEta());
        entity.setRoute(access.get(Route.class, dto.getRouteId()));
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
