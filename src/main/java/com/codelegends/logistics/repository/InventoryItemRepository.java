package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryItemRepository extends ActiveRepository<InventoryItem> {

    @Query("select i from InventoryItem i where i.isActive=true and i.quantity<:threshold")
    List<InventoryItem> below(@Param("threshold") Integer threshold);
}
