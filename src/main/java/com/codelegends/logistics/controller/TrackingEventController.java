package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.TrackingEventDTO;
import com.codelegends.logistics.service.TrackingEventService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking-events")
public class TrackingEventController extends CrudController<TrackingEventDTO> {
    public TrackingEventController(TrackingEventService service) {
        super(service);
    }
}
