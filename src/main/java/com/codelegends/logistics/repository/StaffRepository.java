package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Staff;

/**
 * Provides active-record persistence operations for staff entities.
 */
// Binds Staff persistence to the shared active-record repository contract.
// Uses Staff as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
public interface StaffRepository extends ActiveRepository<Staff> {}
