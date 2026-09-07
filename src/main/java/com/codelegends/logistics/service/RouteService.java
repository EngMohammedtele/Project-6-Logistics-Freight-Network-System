package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for route resources.
 */
// Registers this class as the Spring service for route workflows.
@Service
// Specializes the shared CRUD workflow for Route entities and DTOs.
public class RouteService extends CrudService<Route, RouteDTO> {
    /** Injects persistence access and shared rule validation for Route resources. */
    public RouteService(RouteRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Route.class);
    }

    @Override
    /** Creates a new Route entity instance for create requests. */
    protected Route newEntity() {
        // Returns a blank Route instance that the copy method will populate.
        return new Route();
    }

    @Override
    /** Converts the persisted Route entity to its DTO representation. */
    protected RouteDTO toDTO(Route entity) {
        // Reuses the DTO mapper to expose persisted route values to callers.
        return RouteDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Route entity. */
    protected void copy(RouteDTO dto, Route entity) {
        entity.setRouteDate(dto.getRouteDate());
        entity.setOrigin(dto.getOrigin());
        entity.setDestination(dto.getDestination());
        entity.setStatus(dto.getStatus());
        entity.setVehicle(access.get(Vehicle.class, dto.getVehicleId()));
        entity.setDriver(access.get(Driver.class, dto.getDriverId()));
    }
}
