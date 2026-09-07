package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for vehicle resources.
 */
// Registers this class as the Spring service for vehicle workflows.
@Service
// Specializes the shared CRUD workflow for Vehicle entities and DTOs.
public class VehicleService extends CrudService<Vehicle, VehicleDTO> {
    /** Injects persistence access and shared rule validation for Vehicle resources. */
    public VehicleService(VehicleRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Vehicle.class);
    }

    @Override
    /** Creates a new Vehicle entity instance for create requests. */
    protected Vehicle newEntity() {
        return new Vehicle();
    }

    @Override
    /** Converts the persisted Vehicle entity to its DTO representation. */
    protected VehicleDTO toDTO(Vehicle entity) {
        return VehicleDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Vehicle entity. */
    protected void copy(VehicleDTO dto, Vehicle entity) {
        entity.setPlateNumber(dto.getPlateNumber());
        entity.setType(dto.getType());
        entity.setCapacityKg(dto.getCapacityKg());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
