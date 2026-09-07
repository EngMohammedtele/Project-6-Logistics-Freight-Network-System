package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.InvoiceDTO;
import com.codelegends.logistics.service.InvoiceService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController extends CrudController<InvoiceDTO> {
    public InvoiceController(InvoiceService service) {
        super(service);
    }
}
