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
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Address.class);
    }

    @Override
    /** Creates a new Address entity instance for create requests. */
    protected Address newEntity() {
        // Returns a blank Address instance that the copy method will populate.
        return new Address();
    }

    @Override
    /** Converts the persisted Address entity to its DTO representation. */
    protected AddressDTO toDTO(Address entity) {
        // Reuses the DTO mapper to expose persisted address values to callers.
        return AddressDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Address entity. */
    // Maps request DTO fields onto the mutable Address entity.
    protected void copy(AddressDTO dto, Address entity) {
        // Copies the street line from the address DTO into the entity.
        entity.setStreet(dto.getStreet());
        // Copies the city value used for address persistence.
        entity.setCity(dto.getCity());
        // Copies the postal code used by service-zone address records.
        entity.setPostalCode(dto.getPostalCode());
        // Copies the country value onto the address entity.
        entity.setCountry(dto.getCountry());
        // Resolves the owning customer before attaching it to the address.
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
        // Resolves the service zone that covers the address.
        entity.setServiceZone(access.get(ServiceZone.class, dto.getServiceZoneId()));
    }
}
