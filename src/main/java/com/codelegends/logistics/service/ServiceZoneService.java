package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ServiceZoneService extends CrudService<ServiceZone, ServiceZoneDTO> {
    public ServiceZoneService(ServiceZoneRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, ServiceZone.class);
    }

    @Override
    protected ServiceZone newEntity() {
        return new ServiceZone();
    }

    @Override
    protected ServiceZoneDTO toDTO(ServiceZone entity) {
        return ServiceZoneDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(ServiceZoneDTO dto, ServiceZone entity) {
        entity.setName(dto.getName());
        entity.setRegion(dto.getRegion());
        entity.setBaseRate(dto.getBaseRate());
    }
}
