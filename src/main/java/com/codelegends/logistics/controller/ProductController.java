package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ProductDTO;
import com.codelegends.logistics.service.ProductService;

import org.springframework.web.bind.annotation.*;

/**
 * Handles REST API requests for product resources through the shared CRUD controller.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController extends CrudController<ProductDTO> {
    /** Injects the Product service used by inherited CRUD endpoints. */
    public ProductController(ProductService service) {
        super(service);
    }
}
