package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.RouteDTO;
import com.codelegends.logistics.service.RouteService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController extends CrudController<RouteDTO> {
    public RouteController(RouteService service) {
        super(service);
    }
}
