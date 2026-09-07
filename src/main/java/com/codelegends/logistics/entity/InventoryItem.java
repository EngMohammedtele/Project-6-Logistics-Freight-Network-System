package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(
        name = "inventory_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"warehouse_id", "product_id"}))
@Getter
@Setter
@NoArgsConstructor
public class InventoryItem extends BaseClass {

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "shelf_location", nullable = false, length = 100)
    private String shelfLocation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
