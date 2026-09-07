package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.InvoiceDTO;
import com.codelegends.logistics.service.InvoiceService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for invoice resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController extends CrudController<InvoiceDTO> {
    /** Injects the Invoice service used by inherited CRUD endpoints. */
    public InvoiceController(InvoiceService service) {
        super(service);
    }
}
