package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class DriverService extends CrudService<Driver, DriverDTO> {
    public DriverService(DriverRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Driver.class);
    }

    @Override
    protected Driver newEntity() {
        return new Driver();
    }

    @Override
    protected DriverDTO toDTO(Driver entity) {
        return DriverDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(DriverDTO dto, Driver entity) {
        entity.setName(dto.getName());
        entity.setLicenseNumber(dto.getLicenseNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
