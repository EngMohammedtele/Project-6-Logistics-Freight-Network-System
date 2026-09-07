package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for servicezone resources.
 */
@Service
public class ServiceZoneService extends CrudService<ServiceZone, ServiceZoneDTO> {
    /** Injects persistence access and shared rule validation for ServiceZone resources. */
    public ServiceZoneService(ServiceZoneRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, ServiceZone.class);
    }

    @Override
    /** Creates a new ServiceZone entity instance for create requests. */
    protected ServiceZone newEntity() {
        return new ServiceZone();
    }

    @Override
    /** Converts the persisted ServiceZone entity to its DTO representation. */
    protected ServiceZoneDTO toDTO(ServiceZone entity) {
        return ServiceZoneDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the ServiceZone entity. */
    protected void copy(ServiceZoneDTO dto, ServiceZone entity) {
        entity.setName(dto.getName());
        entity.setRegion(dto.getRegion());
        entity.setBaseRate(dto.getBaseRate());
    }
}
