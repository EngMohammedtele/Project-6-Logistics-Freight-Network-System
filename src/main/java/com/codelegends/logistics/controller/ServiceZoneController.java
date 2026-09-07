package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ServiceZoneDTO;
import com.codelegends.logistics.service.ServiceZoneService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for servicezone resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/service-zones")
public class ServiceZoneController extends CrudController<ServiceZoneDTO> {
    /** Injects the ServiceZone service used by inherited CRUD endpoints. */
    public ServiceZoneController(ServiceZoneService service) {
        super(service);
    }
}
