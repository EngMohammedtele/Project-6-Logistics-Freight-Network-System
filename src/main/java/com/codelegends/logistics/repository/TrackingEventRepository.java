package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.TrackingEvent;

/**
 * Provides active-record persistence operations for tracking event entities.
 */
// Binds TrackingEvent persistence to the shared active-record repository contract.
// Uses TrackingEvent as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for tracking event database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface TrackingEventRepository extends ActiveRepository<TrackingEvent> {}
