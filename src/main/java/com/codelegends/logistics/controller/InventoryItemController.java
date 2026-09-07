package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.InventoryItemDTO;
import com.codelegends.logistics.service.InventoryItemService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory-items")
public class InventoryItemController extends CrudController<InventoryItemDTO> {
    public InventoryItemController(InventoryItemService service) {
        super(service);
    }
}
