package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.StaffDTO;
import com.codelegends.logistics.service.StaffService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController extends CrudController<StaffDTO> {
    public StaffController(StaffService service) {
        super(service);
    }
}
