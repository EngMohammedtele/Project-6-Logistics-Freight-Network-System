package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for carrier resources.
 */
// Registers this class as the Spring service for carrier workflows.
@Service
// Specializes the shared CRUD workflow for Carrier entities and DTOs.
public class CarrierService extends CrudService<Carrier, CarrierDTO> {
    /** Injects persistence access and shared rule validation for Carrier resources. */
    public CarrierService(CarrierRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Carrier.class);
    }

    @Override
    /** Creates a new Carrier entity instance for create requests. */
    protected Carrier newEntity() {
        // Returns a blank Carrier instance that the copy method will populate.
        return new Carrier();
    }

    @Override
    /** Converts the persisted Carrier entity to its DTO representation. */
    protected CarrierDTO toDTO(Carrier entity) {
        return CarrierDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Carrier entity. */
    protected void copy(CarrierDTO dto, Carrier entity) {
        entity.setName(dto.getName());
        entity.setContactEmail(dto.getContactEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCountry(dto.getCountry());
    }
}
