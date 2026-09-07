package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ShipmentService extends CrudService<Shipment, ShipmentDTO> {
    public ShipmentService(ShipmentRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Shipment.class);
    }

    @Override
    protected Shipment newEntity() {
        return new Shipment();
    }

    @Override
    protected ShipmentDTO toDTO(Shipment entity) {
        return ShipmentDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(ShipmentDTO dto, Shipment entity) {
        entity.setShipmentDate(dto.getShipmentDate());
        entity.setStatus(dto.getStatus());
        entity.setTotalWeight(dto.getTotalWeight());
        entity.setWarehouse(access.get(Warehouse.class, dto.getWarehouseId()));
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
        entity.setCarrier(access.get(Carrier.class, dto.getCarrierId()));
    }
}
