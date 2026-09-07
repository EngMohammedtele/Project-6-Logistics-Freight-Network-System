package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

// Imports Query so the repository can declare explicit JPQL statements.
import org.springframework.data.jpa.repository.Query;
// Imports Param to bind method arguments into named JPQL parameters.
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for inventory item entities.
 */
// Binds InventoryItem persistence to the shared active-record repository contract.
// Uses InventoryItem as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for inventory item database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface InventoryItemRepository extends ActiveRepository<InventoryItem> {

    // Executes a custom JPQL query instead of relying only on method-name derivation.
    // Filters active inventory rows whose quantity is below the requested threshold.
    @Query("select i from InventoryItem i where i.isActive=true and i.quantity<:threshold")
    /** Finds active inventory records below the supplied quantity threshold. */
    // Returns every active inventory item that should be considered low stock.
    // Uses the threshold argument as the upper bound for matching quantities.
    // Supports replenishment screens that need below-threshold stock records.
    // Binds the threshold argument to the JPQL quantity comparison.
    // Keeps the named parameter aligned with the query placeholder.
    List<InventoryItem> below(@Param("threshold") Integer threshold);
}
