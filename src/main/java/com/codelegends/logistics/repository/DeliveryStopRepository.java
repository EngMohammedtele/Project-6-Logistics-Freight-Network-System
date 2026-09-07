package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.DeliveryStop;

/**
 * Provides active-record persistence operations for delivery stop entities.
 */
// Binds DeliveryStop persistence to the shared active-record repository contract.
// Uses DeliveryStop as the aggregate root for inherited CRUD operations.
public interface DeliveryStopRepository extends ActiveRepository<DeliveryStop> {}
