package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents product stock held at a warehouse shelf location.
 */
// Registers InventoryItem as a persistent JPA entity.
@Entity
// Defines the database table metadata used for warehouse inventory item rows.
@Table(
        name = "inventory_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"warehouse_id", "product_id"}))
// Generates read accessors for persisted warehouse inventory item properties.
@Getter
// Generates write accessors used by JPA and service mapping for warehouse inventory item.
@Setter
// Provides the default constructor required by JPA for warehouse inventory item hydration.
@NoArgsConstructor
public class InventoryItem extends BaseClass {

    @Column(name = "quantity", nullable = false)
    /** Stores the item or inventory quantity. */
    private Integer quantity;

    @Column(name = "shelf_location", nullable = false, length = 100)
    /** Stores the warehouse shelf location for inventory placement. */
    private String shelfLocation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    /** Defines the warehouse relationship used by this logistics record. */
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    /** Defines the product associated with this inventory or shipment item. */
    private Product product;
}
