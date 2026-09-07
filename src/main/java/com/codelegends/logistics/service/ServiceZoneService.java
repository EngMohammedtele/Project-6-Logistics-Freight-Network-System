package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for service zone resources.
 */
// Registers this class as the Spring service for service zone workflows.
@Service
// Specializes the shared CRUD workflow for ServiceZone entities and DTOs.
public class ServiceZoneService extends CrudService<ServiceZone, ServiceZoneDTO> {
    /** Injects persistence access and shared rule validation for ServiceZone resources. */
    public ServiceZoneService(ServiceZoneRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, ServiceZone.class);
    }

    @Override
    /** Creates a new ServiceZone entity instance for create requests. */
    protected ServiceZone newEntity() {
        // Returns a blank ServiceZone instance that the copy method will populate.
        return new ServiceZone();
    }

    @Override
    /** Converts the persisted ServiceZone entity to its DTO representation. */
    protected ServiceZoneDTO toDTO(ServiceZone entity) {
        // Reuses the DTO mapper to expose persisted service zone values to callers.
        return ServiceZoneDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the ServiceZone entity. */
    // Maps request DTO fields onto the mutable ServiceZone entity.
    protected void copy(ServiceZoneDTO dto, ServiceZone entity) {
        // Copies the service zone display name.
        entity.setName(dto.getName());
        // Copies the geographic region served by the zone.
        entity.setRegion(dto.getRegion());
        // Copies the base delivery rate used for zone pricing.
        entity.setBaseRate(dto.getBaseRate());
    }
}
