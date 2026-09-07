package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.DeliveryStopDTO;
import com.codelegends.logistics.service.DeliveryStopService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for deliverystop resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/delivery-stops")
public class DeliveryStopController extends CrudController<DeliveryStopDTO> {
    /** Injects the DeliveryStop service used by inherited CRUD endpoints. */
    public DeliveryStopController(DeliveryStopService service) {
        super(service);
    }
}
