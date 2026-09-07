package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.WarehouseDTO;
import com.codelegends.logistics.service.WarehouseService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for warehouse resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController extends CrudController<WarehouseDTO> {
    /** Injects the Warehouse service used by inherited CRUD endpoints. */
    public WarehouseController(WarehouseService service) {
        super(service);
    }
}
