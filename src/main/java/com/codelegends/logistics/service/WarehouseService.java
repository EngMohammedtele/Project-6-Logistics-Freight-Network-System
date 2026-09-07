package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for warehouse resources.
 */
// Registers this class as the Spring service for warehouse workflows.
@Service
// Specializes the shared CRUD workflow for Warehouse entities and DTOs.
public class WarehouseService extends CrudService<Warehouse, WarehouseDTO> {
    /** Injects persistence access and shared rule validation for Warehouse resources. */
    public WarehouseService(WarehouseRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Warehouse.class);
    }

    @Override
    /** Creates a new Warehouse entity instance for create requests. */
    protected Warehouse newEntity() {
        // Returns a blank Warehouse instance that the copy method will populate.
        return new Warehouse();
    }

    @Override
    /** Converts the persisted Warehouse entity to its DTO representation. */
    protected WarehouseDTO toDTO(Warehouse entity) {
        // Reuses the DTO mapper to expose persisted warehouse values to callers.
        return WarehouseDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Warehouse entity. */
    // Maps request DTO fields onto the mutable Warehouse entity.
    protected void copy(WarehouseDTO dto, Warehouse entity) {
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setCapacity(dto.getCapacity());
    }
}
