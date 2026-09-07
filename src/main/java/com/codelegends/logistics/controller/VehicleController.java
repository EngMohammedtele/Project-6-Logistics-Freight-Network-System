package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.VehicleDTO;
import com.codelegends.logistics.service.VehicleService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController extends CrudController<VehicleDTO> {
    public VehicleController(VehicleService service) {
        super(service);
    }
}
