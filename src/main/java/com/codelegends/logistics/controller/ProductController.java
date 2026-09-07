package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.ProductDTO;
import com.codelegends.logistics.service.ProductService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController extends CrudController<ProductDTO> {
    public ProductController(ProductService service) {
        super(service);
    }
}
