package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.AddressDTO;
import com.codelegends.logistics.service.AddressService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for address resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressController extends CrudController<AddressDTO> {
    /** Injects the Address service used by inherited CRUD endpoints. */
    public AddressController(AddressService service) {
        super(service);
    }
}
