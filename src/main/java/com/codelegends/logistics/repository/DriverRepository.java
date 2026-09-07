package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Driver;

/**
 * Provides active-record persistence operations for driver entities.
 */
// Binds Driver persistence to the shared active-record repository contract.
// Uses Driver as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for driver database access.
public interface DriverRepository extends ActiveRepository<Driver> {}
