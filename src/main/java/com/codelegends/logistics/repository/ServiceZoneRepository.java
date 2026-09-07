package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.ServiceZone;

/**
 * Provides active-record persistence operations for service zone entities.
 */
// Binds ServiceZone persistence to the shared active-record repository contract.
// Uses ServiceZone as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for service zone database access.
public interface ServiceZoneRepository extends ActiveRepository<ServiceZone> {}
