package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for warehouse resources.
 */
@Service
public class WarehouseService extends CrudService<Warehouse, WarehouseDTO> {
    /** Injects persistence access and shared rule validation for Warehouse resources. */
    public WarehouseService(WarehouseRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Warehouse.class);
    }

    @Override
    /** Creates a new Warehouse entity instance for create requests. */
    protected Warehouse newEntity() {
        return new Warehouse();
    }

    @Override
    /** Converts the persisted Warehouse entity to its DTO representation. */
    protected WarehouseDTO toDTO(Warehouse entity) {
        return WarehouseDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Warehouse entity. */
    protected void copy(WarehouseDTO dto, Warehouse entity) {
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setCapacity(dto.getCapacity());
    }
}
