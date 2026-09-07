package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for shipment item resources.
 */
// Registers this class as the Spring service for shipment item workflows.
@Service
// Specializes the shared CRUD workflow for ShipmentItem entities and DTOs.
public class ShipmentItemService extends CrudService<ShipmentItem, ShipmentItemDTO> {
    /** Injects persistence access and shared rule validation for ShipmentItem resources. */
    public ShipmentItemService(
            ShipmentItemRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, ShipmentItem.class);
    }

    @Override
    /** Creates a new ShipmentItem entity instance for create requests. */
    protected ShipmentItem newEntity() {
        // Returns a blank ShipmentItem instance that the copy method will populate.
        return new ShipmentItem();
    }

    @Override
    /** Converts the persisted ShipmentItem entity to its DTO representation. */
    protected ShipmentItemDTO toDTO(ShipmentItem entity) {
        // Reuses the DTO mapper to expose persisted shipment item values to callers.
        return ShipmentItemDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the ShipmentItem entity. */
    // Maps request DTO fields onto the mutable ShipmentItem entity.
    protected void copy(ShipmentItemDTO dto, ShipmentItem entity) {
        // Copies the requested product quantity for the shipment line.
        entity.setQuantity(dto.getQuantity());
        // Resolves the shipment that owns this line item.
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
        entity.setProduct(access.get(Product.class, dto.getProductId()));
    }
}
