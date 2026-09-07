package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Warehouse;

/**
 * Provides active-record persistence operations for warehouse entities.
 */
// Binds Warehouse persistence to the shared active-record repository contract.
// Uses Warehouse as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for warehouse database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface WarehouseRepository extends ActiveRepository<Warehouse> {}
