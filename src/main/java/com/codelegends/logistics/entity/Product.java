package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/**
 * Represents a shippable product with a SKU and weight.
 */
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"sku"}))
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseClass {

    @Column(name = "name", nullable = false, length = 120)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "sku", nullable = false, length = 50)
    /** Stores the unique product stock keeping unit. */
    private String sku;

    @Column(name = "weight_kg", nullable = false, precision = 12, scale = 2)
    /** Stores product weight in kilograms for shipment weight calculations. */
    private BigDecimal weightKg;

    @Column(name = "category", nullable = false, length = 100)
    /** Stores the product category used for catalog grouping. */
    private String category;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "product")
    /** Maintains warehouse or product inventory records. */
    private List<InventoryItem> inventoryItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "product")
    /** Maintains shipment item records for the shipment or product. */
    private List<ShipmentItem> shipmentItemList = new ArrayList<>();
}
