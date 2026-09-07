package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ServiceZoneDTO;
import com.codelegends.logistics.service.ServiceZoneService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-zones")
public class ServiceZoneController extends CrudController<ServiceZoneDTO> {
    public ServiceZoneController(ServiceZoneService service) {
        super(service);
    }
}
