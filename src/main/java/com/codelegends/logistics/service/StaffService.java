package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for staff resources.
 */
// Registers this class as the Spring service for staff workflows.
@Service
// Specializes the shared CRUD workflow for Staff entities and DTOs.
public class StaffService extends CrudService<Staff, StaffDTO> {
    /** Injects persistence access and shared rule validation for Staff resources. */
    public StaffService(StaffRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Staff.class);
    }

    @Override
    /** Creates a new Staff entity instance for create requests. */
    protected Staff newEntity() {
        // Returns a blank Staff instance that the copy method will populate.
        return new Staff();
    }

    @Override
    /** Converts the persisted Staff entity to its DTO representation. */
    protected StaffDTO toDTO(Staff entity) {
        return StaffDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Staff entity. */
    protected void copy(StaffDTO dto, Staff entity) {
        entity.setName(dto.getName());
        entity.setRole(dto.getRole());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
    }
}
