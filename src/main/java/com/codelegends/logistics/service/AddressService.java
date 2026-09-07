package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class AddressService extends CrudService<Address, AddressDTO> {
    public AddressService(AddressRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Address.class);
    }

    @Override
    protected Address newEntity() {
        return new Address();
    }

    @Override
    protected AddressDTO toDTO(Address entity) {
        return AddressDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(AddressDTO dto, Address entity) {
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
        entity.setServiceZone(access.get(ServiceZone.class, dto.getServiceZoneId()));
    }
}
