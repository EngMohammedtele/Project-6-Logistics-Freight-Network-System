package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for shipment resources.
 */
// Registers this class as the Spring service for shipment workflows.
@Service
// Specializes the shared CRUD workflow for Shipment entities and DTOs.
public class ShipmentService extends CrudService<Shipment, ShipmentDTO> {
    /** Injects persistence access and shared rule validation for Shipment resources. */
    public ShipmentService(ShipmentRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Shipment.class);
    }

    @Override
    /** Creates a new Shipment entity instance for create requests. */
    protected Shipment newEntity() {
        // Returns a blank Shipment instance that the copy method will populate.
        return new Shipment();
    }

    @Override
    /** Converts the persisted Shipment entity to its DTO representation. */
    protected ShipmentDTO toDTO(Shipment entity) {
        return ShipmentDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Shipment entity. */
    protected void copy(ShipmentDTO dto, Shipment entity) {
        entity.setShipmentDate(dto.getShipmentDate());
        entity.setStatus(dto.getStatus());
        entity.setTotalWeight(dto.getTotalWeight());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
