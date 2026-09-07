package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.VehicleDTO;
import com.codelegends.logistics.service.VehicleService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for vehicle resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController extends CrudController<VehicleDTO> {
    /** Injects the Vehicle service used by inherited CRUD endpoints. */
    public VehicleController(VehicleService service) {
        super(service);
    }
}
