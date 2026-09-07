package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Carrier;

/**
 * Provides active-record persistence operations for carrier entities.
 */
// Binds Carrier persistence to the shared active-record repository contract.
// Uses Carrier as the aggregate root for inherited CRUD operations.
public interface CarrierRepository extends ActiveRepository<Carrier> {}
