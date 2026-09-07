package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Product;

/**
 * Provides active-record persistence operations for product entities.
 */
// Binds Product persistence to the shared active-record repository contract.
// Uses Product as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
public interface ProductRepository extends ActiveRepository<Product> {}
