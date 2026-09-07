package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class RouteService extends CrudService<Route, RouteDTO> {
    public RouteService(RouteRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Route.class);
    }

    @Override
    protected Route newEntity() {
        return new Route();
    }

    @Override
    protected RouteDTO toDTO(Route entity) {
        return RouteDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(RouteDTO dto, Route entity) {
        entity.setRouteDate(dto.getRouteDate());
        entity.setOrigin(dto.getOrigin());
        entity.setDestination(dto.getDestination());
        entity.setStatus(dto.getStatus());
        entity.setVehicle(access.get(Vehicle.class, dto.getVehicleId()));
        entity.setDriver(access.get(Driver.class, dto.getDriverId()));
    }
}
