package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.StaffDTO;
import com.codelegends.logistics.service.StaffService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for staff resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/staff")
public class StaffController extends CrudController<StaffDTO> {
    /** Injects the Staff service used by inherited CRUD endpoints. */
    public StaffController(StaffService service) {
        super(service);
    }
}
