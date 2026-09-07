package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for inventory item resources.
 */
// Registers this class as the Spring service for inventory item workflows.
@Service
// Specializes the shared CRUD workflow for InventoryItem entities and DTOs.
public class InventoryItemService extends CrudService<InventoryItem, InventoryItemDTO> {
    /** Injects persistence access and shared rule validation for InventoryItem resources. */
    public InventoryItemService(
            InventoryItemRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, InventoryItem.class);
    }

    @Override
    /** Creates a new InventoryItem entity instance for create requests. */
    protected InventoryItem newEntity() {
        // Returns a blank InventoryItem instance that the copy method will populate.
        return new InventoryItem();
    }

    @Override
    /** Converts the persisted InventoryItem entity to its DTO representation. */
    protected InventoryItemDTO toDTO(InventoryItem entity) {
        // Reuses the DTO mapper to expose persisted inventory item values to callers.
        return InventoryItemDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the InventoryItem entity. */
    // Maps request DTO fields onto the mutable InventoryItem entity.
    protected void copy(InventoryItemDTO dto, InventoryItem entity) {
        // Copies the inventory quantity tracked at the warehouse.
        entity.setQuantity(dto.getQuantity());
        // Copies the shelf location used for stock placement.
        entity.setShelfLocation(dto.getShelfLocation());
        // Resolves the warehouse that stores this inventory item.
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
        // Resolves the product represented by this inventory record.
        entity.setProduct(access.get(Product.class, dto.getProductId()));
    }
}
