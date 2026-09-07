package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a storage location that holds inventory and dispatches shipments.
 */
// Registers Warehouse as a persistent JPA entity.
@Entity
// Defines the database table metadata used for storage warehouse rows.
@Table(name = "warehouse")
// Generates read accessors for persisted storage warehouse properties.
@Getter
// Generates write accessors used by JPA and service mapping for storage warehouse.
@Setter
@NoArgsConstructor
public class Warehouse extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "location", nullable = false, length = 200)
    /** Stores where the tracking event was recorded. */
    private String location;

    @Column(name = "capacity", nullable = false)
    /** Stores the maximum inventory units supported by the warehouse. */
    private Integer capacity;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    /** Maintains warehouse or product inventory records. */
    private List<InventoryItem> inventoryItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    /** Defines the shipment associated with this record. */
    /** Maintains shipments associated with the customer, carrier, or warehouse. */
    private List<Shipment> shipmentList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Staff> staffList = new ArrayList<>();
}
