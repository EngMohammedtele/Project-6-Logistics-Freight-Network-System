package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Address;

/**
 * Provides active-record persistence operations for address entities.
 */
// Binds Address persistence to the shared active-record repository contract.
// Uses Address as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for address database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface AddressRepository extends ActiveRepository<Address> {}
