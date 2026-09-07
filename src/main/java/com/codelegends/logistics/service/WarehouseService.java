package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends CrudService<Warehouse, WarehouseDTO> {
    public WarehouseService(WarehouseRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Warehouse.class);
    }

    @Override
    protected Warehouse newEntity() {
        return new Warehouse();
    }

    @Override
    protected WarehouseDTO toDTO(Warehouse entity) {
        return WarehouseDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(WarehouseDTO dto, Warehouse entity) {
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setCapacity(dto.getCapacity());
    }
}
