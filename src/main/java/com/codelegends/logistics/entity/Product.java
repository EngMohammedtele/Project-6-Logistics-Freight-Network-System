package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"sku"}))
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseClass {

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "weight_kg", nullable = false, precision = 12, scale = 2)
    private BigDecimal weightKg;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<InventoryItem> inventoryItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ShipmentItem> shipmentItemList = new ArrayList<>();
}
