package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class InventoryItemService extends CrudService<InventoryItem, InventoryItemDTO> {
    public InventoryItemService(
            InventoryItemRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, InventoryItem.class);
    }

    @Override
    protected InventoryItem newEntity() {
        return new InventoryItem();
    }

    @Override
    protected InventoryItemDTO toDTO(InventoryItem entity) {
        return InventoryItemDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(InventoryItemDTO dto, InventoryItem entity) {
        entity.setQuantity(dto.getQuantity());
        entity.setShelfLocation(dto.getShelfLocation());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
        entity.setProduct(access.get(Product.class, dto.getProductId()));
    }
}
