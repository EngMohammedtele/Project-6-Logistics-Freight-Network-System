package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for product resources.
 */
// Registers this class as the Spring service for product workflows.
@Service
// Specializes the shared CRUD workflow for Product entities and DTOs.
public class ProductService extends CrudService<Product, ProductDTO> {
    /** Injects persistence access and shared rule validation for Product resources. */
    public ProductService(ProductRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Product.class);
    }

    @Override
    /** Creates a new Product entity instance for create requests. */
    protected Product newEntity() {
        return new Product();
    }

    @Override
    /** Converts the persisted Product entity to its DTO representation. */
    protected ProductDTO toDTO(Product entity) {
        return ProductDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Product entity. */
    protected void copy(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setSku(dto.getSku());
        entity.setWeightKg(dto.getWeightKg());
        entity.setCategory(dto.getCategory());
    }
}
