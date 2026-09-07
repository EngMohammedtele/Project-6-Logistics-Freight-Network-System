package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.Staff;

/**
 * Provides active-record persistence operations for staff entities.
 */
// Binds Staff persistence to the shared active-record repository contract.
public interface StaffRepository extends ActiveRepository<Staff> {}
