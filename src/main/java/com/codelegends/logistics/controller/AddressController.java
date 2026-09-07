package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.AddressDTO;
import com.codelegends.logistics.service.AddressService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController extends CrudController<AddressDTO> {
    public AddressController(AddressService service) {
        super(service);
    }
}
