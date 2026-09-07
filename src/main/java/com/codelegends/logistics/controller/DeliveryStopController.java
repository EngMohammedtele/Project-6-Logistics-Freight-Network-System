package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.DeliveryStopDTO;
import com.codelegends.logistics.service.DeliveryStopService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-stops")
public class DeliveryStopController extends CrudController<DeliveryStopDTO> {
    public DeliveryStopController(DeliveryStopService service) {
        super(service);
    }
}
