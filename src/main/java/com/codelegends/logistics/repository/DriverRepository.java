package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Driver;

/**
 * Provides active-record persistence operations for driver entities.
 */
// Binds Driver persistence to the shared active-record repository contract.
public interface DriverRepository extends ActiveRepository<Driver> {}
