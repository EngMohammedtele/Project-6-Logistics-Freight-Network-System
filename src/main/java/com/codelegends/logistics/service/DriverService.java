package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for driver resources.
 */
@Service
public class DriverService extends CrudService<Driver, DriverDTO> {
    /** Injects persistence access and shared rule validation for Driver resources. */
    public DriverService(DriverRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Driver.class);
    }

    @Override
    /** Creates a new Driver entity instance for create requests. */
    protected Driver newEntity() {
        return new Driver();
    }

    @Override
    /** Converts the persisted Driver entity to its DTO representation. */
    protected DriverDTO toDTO(Driver entity) {
        return DriverDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Driver entity. */
    protected void copy(DriverDTO dto, Driver entity) {
        entity.setName(dto.getName());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
