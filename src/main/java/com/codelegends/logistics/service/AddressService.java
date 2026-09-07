package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for address resources.
 */
// Registers this class as the Spring service for address workflows.
@Service
// Specializes the shared CRUD workflow for Address entities and DTOs.
public class AddressService extends CrudService<Address, AddressDTO> {
    /** Injects persistence access and shared rule validation for Address resources. */
    public AddressService(AddressRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Address.class);
    }

    @Override
    /** Creates a new Address entity instance for create requests. */
    protected Address newEntity() {
        return new Address();
    }

    @Override
    /** Converts the persisted Address entity to its DTO representation. */
    protected AddressDTO toDTO(Address entity) {
        return AddressDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Address entity. */
    protected void copy(AddressDTO dto, Address entity) {
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
        entity.setServiceZone(access.get(ServiceZone.class, dto.getServiceZoneId()));
    }
}
