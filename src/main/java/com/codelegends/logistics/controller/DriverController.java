package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.DriverDTO;
import com.codelegends.logistics.service.DriverService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for driver resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController extends CrudController<DriverDTO> {
    /** Injects the Driver service used by inherited CRUD endpoints. */
    public DriverController(DriverService service) {
        super(service);
    }
}
