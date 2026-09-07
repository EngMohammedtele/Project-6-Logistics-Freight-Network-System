package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.ShipmentItem;

/**
 * Provides active-record persistence operations for shipment item entities.
 */
// Binds ShipmentItem persistence to the shared active-record repository contract.
// Uses ShipmentItem as the aggregate root for inherited CRUD operations.
public interface ShipmentItemRepository extends ActiveRepository<ShipmentItem> {}
