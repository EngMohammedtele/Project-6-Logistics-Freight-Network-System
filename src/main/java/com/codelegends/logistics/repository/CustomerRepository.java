package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Customer;

/**
 * Provides active-record persistence operations for customer entities.
 */
// Binds Customer persistence to the shared active-record repository contract.
public interface CustomerRepository extends ActiveRepository<Customer> {}
