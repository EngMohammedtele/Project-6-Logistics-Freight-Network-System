package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.TrackingEventDTO;
import com.codelegends.logistics.service.TrackingEventService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for trackingevent resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/tracking-events")
public class TrackingEventController extends CrudController<TrackingEventDTO> {
    /** Injects the TrackingEvent service used by inherited CRUD endpoints. */
    public TrackingEventController(TrackingEventService service) {
        super(service);
    }
}
