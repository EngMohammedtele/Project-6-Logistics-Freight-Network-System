package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for inventoryitem entities.
 */
public interface InventoryItemRepository extends ActiveRepository<InventoryItem> {

    @Query("select i from InventoryItem i where i.isActive=true and i.quantity<:threshold")
    /** Finds active inventory records below the supplied quantity threshold. */
    List<InventoryItem> below(@Param("threshold") Integer threshold);
}
