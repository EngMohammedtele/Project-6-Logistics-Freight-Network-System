package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.WarehouseDTO;
import com.codelegends.logistics.service.WarehouseService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController extends CrudController<WarehouseDTO> {
    public WarehouseController(WarehouseService service) {
        super(service);
    }
}
