package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.DriverDTO;
import com.codelegends.logistics.service.DriverService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController extends CrudController<DriverDTO> {
    public DriverController(DriverService service) {
        super(service);
    }
}
