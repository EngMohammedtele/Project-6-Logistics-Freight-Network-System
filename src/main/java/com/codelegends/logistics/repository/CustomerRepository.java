package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Customer;

/**
 * Provides active-record persistence operations for customer entities.
 */
// Binds Customer persistence to the shared active-record repository contract.
// Uses Customer as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for customer database access.
public interface CustomerRepository extends ActiveRepository<Customer> {}
