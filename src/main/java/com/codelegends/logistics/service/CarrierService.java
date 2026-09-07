package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class CarrierService extends CrudService<Carrier, CarrierDTO> {
    public CarrierService(CarrierRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Carrier.class);
    }

    @Override
    protected Carrier newEntity() {
        return new Carrier();
    }

    @Override
    protected CarrierDTO toDTO(Carrier entity) {
        return CarrierDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(CarrierDTO dto, Carrier entity) {
        entity.setName(dto.getName());
        entity.setContactEmail(dto.getContactEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCountry(dto.getCountry());
    }
}
