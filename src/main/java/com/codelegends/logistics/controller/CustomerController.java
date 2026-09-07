package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.CustomerDTO;
import com.codelegends.logistics.service.CustomerService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for customer resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController extends CrudController<CustomerDTO> {
    /** Injects the Customer service used by inherited CRUD endpoints. */
    public CustomerController(CustomerService service) {
        super(service);
    }
}
