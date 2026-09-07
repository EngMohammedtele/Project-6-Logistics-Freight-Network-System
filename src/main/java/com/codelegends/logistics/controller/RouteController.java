package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.RouteDTO;
import com.codelegends.logistics.service.RouteService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for route resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/routes")
public class RouteController extends CrudController<RouteDTO> {
    /** Injects the Route service used by inherited CRUD endpoints. */
    public RouteController(RouteService service) {
        super(service);
    }
}
