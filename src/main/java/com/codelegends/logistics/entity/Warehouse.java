package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "warehouse")
@Getter
@Setter
@NoArgsConstructor
public class Warehouse extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<InventoryItem> inventoryItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Shipment> shipmentList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Staff> staffList = new ArrayList<>();
}
