package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class StaffService extends CrudService<Staff, StaffDTO> {
    public StaffService(StaffRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Staff.class);
    }

    @Override
    protected Staff newEntity() {
        return new Staff();
    }

    @Override
    protected StaffDTO toDTO(Staff entity) {
        return StaffDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(StaffDTO dto, Staff entity) {
        entity.setName(dto.getName());
        entity.setRole(dto.getRole());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
    }
}
