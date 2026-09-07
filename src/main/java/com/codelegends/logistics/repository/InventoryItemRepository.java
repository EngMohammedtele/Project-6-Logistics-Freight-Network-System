package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for inventory item entities.
 */
// Binds InventoryItem persistence to the shared active-record repository contract.
// Uses InventoryItem as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for inventory item database access.
public interface InventoryItemRepository extends ActiveRepository<InventoryItem> {

    @Query("select i from InventoryItem i where i.isActive=true and i.quantity<:threshold")
    /** Finds active inventory records below the supplied quantity threshold. */
    List<InventoryItem> below(@Param("threshold") Integer threshold);
}
