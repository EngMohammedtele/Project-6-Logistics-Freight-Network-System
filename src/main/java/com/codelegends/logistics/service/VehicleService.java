package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class VehicleService extends CrudService<Vehicle, VehicleDTO> {
    public VehicleService(VehicleRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Vehicle.class);
    }

    @Override
    protected Vehicle newEntity() {
        return new Vehicle();
    }

    @Override
    protected VehicleDTO toDTO(Vehicle entity) {
        return VehicleDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(VehicleDTO dto, Vehicle entity) {
        entity.setPlateNumber(dto.getPlateNumber());
        entity.setType(dto.getType());
        entity.setCapacityKg(dto.getCapacityKg());
        entity.setStatus(dto.getStatus());
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
