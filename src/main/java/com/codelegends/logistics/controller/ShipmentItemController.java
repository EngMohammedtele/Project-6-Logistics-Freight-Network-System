package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ShipmentItemDTO;
import com.codelegends.logistics.service.ShipmentItemService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipment-items")
public class ShipmentItemController extends CrudController<ShipmentItemDTO> {
    public ShipmentItemController(ShipmentItemService service) {
        super(service);
    }
}
