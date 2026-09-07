package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.CustomerDTO;
import com.codelegends.logistics.service.CustomerService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController extends CrudController<CustomerDTO> {
    public CustomerController(CustomerService service) {
        super(service);
    }
}
