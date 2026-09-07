package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ShipmentItemDTO;
import com.codelegends.logistics.service.ShipmentItemService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for shipmentitem resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/shipment-items")
public class ShipmentItemController extends CrudController<ShipmentItemDTO> {
    /** Injects the ShipmentItem service used by inherited CRUD endpoints. */
    public ShipmentItemController(ShipmentItemService service) {
        super(service);
    }
}
