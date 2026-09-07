package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.TrackingEvent;

/**
 * Provides active-record persistence operations for tracking event entities.
 */
// Binds TrackingEvent persistence to the shared active-record repository contract.
// Uses TrackingEvent as the aggregate root for inherited CRUD operations.
public interface TrackingEventRepository extends ActiveRepository<TrackingEvent> {}
