package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ShipmentDTO;
import com.codelegends.logistics.service.ShipmentService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for shipment resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController extends CrudController<ShipmentDTO> {
    /** Injects the Shipment service used by inherited CRUD endpoints. */
    public ShipmentController(ShipmentService service) {
        super(service);
    }
}
