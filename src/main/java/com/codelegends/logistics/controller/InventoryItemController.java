package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.InventoryItemDTO;
import com.codelegends.logistics.service.InventoryItemService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for inventoryitem resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/inventory-items")
public class InventoryItemController extends CrudController<InventoryItemDTO> {
    /** Injects the InventoryItem service used by inherited CRUD endpoints. */
    public InventoryItemController(InventoryItemService service) {
        super(service);
    }
}
