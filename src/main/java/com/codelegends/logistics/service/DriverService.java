package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for driver resources.
 */
// Registers this class as the Spring service for driver workflows.
@Service
// Specializes the shared CRUD workflow for Driver entities and DTOs.
public class DriverService extends CrudService<Driver, DriverDTO> {
    /** Injects persistence access and shared rule validation for Driver resources. */
    public DriverService(DriverRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Driver.class);
    }

    @Override
    /** Creates a new Driver entity instance for create requests. */
    protected Driver newEntity() {
        // Returns a blank Driver instance that the copy method will populate.
        return new Driver();
    }

    @Override
    /** Converts the persisted Driver entity to its DTO representation. */
    protected DriverDTO toDTO(Driver entity) {
        // Reuses the DTO mapper to expose persisted driver values to callers.
        return DriverDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Driver entity. */
    // Maps request DTO fields onto the mutable Driver entity.
    protected void copy(DriverDTO dto, Driver entity) {
        // Copies the driver name used in route assignment views.
        entity.setName(dto.getName());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
