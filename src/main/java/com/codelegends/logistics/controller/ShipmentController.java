package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ShipmentDTO;
import com.codelegends.logistics.service.ShipmentService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController extends CrudController<ShipmentDTO> {
    public ShipmentController(ShipmentService service) {
        super(service);
    }
}
