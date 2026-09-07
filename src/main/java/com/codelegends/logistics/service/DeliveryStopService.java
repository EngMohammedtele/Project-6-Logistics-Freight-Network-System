package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class DeliveryStopService extends CrudService<DeliveryStop, DeliveryStopDTO> {
    public DeliveryStopService(
            DeliveryStopRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, DeliveryStop.class);
    }

    @Override
    protected DeliveryStop newEntity() {
        return new DeliveryStop();
    }

    @Override
    protected DeliveryStopDTO toDTO(DeliveryStop entity) {
        return DeliveryStopDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(DeliveryStopDTO dto, DeliveryStop entity) {
        entity.setSequence(dto.getSequence());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setEta(dto.getEta());
        entity.setRoute(access.get(Route.class, dto.getRouteId()));
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
    }
}
