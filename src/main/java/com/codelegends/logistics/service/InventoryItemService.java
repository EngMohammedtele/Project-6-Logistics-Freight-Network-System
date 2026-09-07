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
        super(repository, access, rules, InventoryItem.class);
    }

    @Override
    /** Creates a new InventoryItem entity instance for create requests. */
    protected InventoryItem newEntity() {
        return new InventoryItem();
    }

    @Override
    /** Converts the persisted InventoryItem entity to its DTO representation. */
    protected InventoryItemDTO toDTO(InventoryItem entity) {
        return InventoryItemDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the InventoryItem entity. */
    protected void copy(InventoryItemDTO dto, InventoryItem entity) {
        entity.setQuantity(dto.getQuantity());
        entity.setShelfLocation(dto.getShelfLocation());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
        entity.setProduct(access.get(Product.class, dto.getProductId()));
    }
}
