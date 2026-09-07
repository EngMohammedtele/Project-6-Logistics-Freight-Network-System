package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ShipmentItemService extends CrudService<ShipmentItem, ShipmentItemDTO> {
    public ShipmentItemService(
            ShipmentItemRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, ShipmentItem.class);
    }

    @Override
    protected ShipmentItem newEntity() {
        return new ShipmentItem();
    }

    @Override
    protected ShipmentItemDTO toDTO(ShipmentItem entity) {
        return ShipmentItemDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(ShipmentItemDTO dto, ShipmentItem entity) {
        entity.setQuantity(dto.getQuantity());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
        entity.setProduct(access.get(Product.class, dto.getProductId()));
    }
}
