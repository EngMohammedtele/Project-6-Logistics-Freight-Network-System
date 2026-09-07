package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<Product, ProductDTO> {
    public ProductService(ProductRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Product.class);
    }

    @Override
    protected Product newEntity() {
        return new Product();
    }

    @Override
    protected ProductDTO toDTO(Product entity) {
        return ProductDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setSku(dto.getSku());
        entity.setWeightKg(dto.getWeightKg());
        entity.setCategory(dto.getCategory());
    }
}
