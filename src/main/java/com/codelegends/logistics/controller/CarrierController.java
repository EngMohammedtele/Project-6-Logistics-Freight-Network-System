package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.CarrierDTO;
import com.codelegends.logistics.service.CarrierService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController extends CrudController<CarrierDTO> {
    public CarrierController(CarrierService service) {
        super(service);
    }
}
