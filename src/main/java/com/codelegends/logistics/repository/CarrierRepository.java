package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Carrier;

/**
 * Provides active-record persistence operations for carrier entities.
 */
// Binds Carrier persistence to the shared active-record repository contract.
// Uses Carrier as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for carrier database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface CarrierRepository extends ActiveRepository<Carrier> {}
