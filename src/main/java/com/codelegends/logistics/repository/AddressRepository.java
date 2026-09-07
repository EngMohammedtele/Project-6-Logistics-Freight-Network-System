package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Address;

/**
 * Provides active-record persistence operations for address entities.
 */
// Binds Address persistence to the shared active-record repository contract.
// Uses Address as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
public interface AddressRepository extends ActiveRepository<Address> {}
