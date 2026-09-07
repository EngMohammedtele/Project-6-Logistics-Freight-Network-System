package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.CarrierDTO;
import com.codelegends.logistics.service.CarrierService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for carrier resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/carriers")
public class CarrierController extends CrudController<CarrierDTO> {
    /** Injects the Carrier service used by inherited CRUD endpoints. */
    public CarrierController(CarrierService service) {
        super(service);
    }
}
