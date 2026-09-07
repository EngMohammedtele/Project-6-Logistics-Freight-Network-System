package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "shipment")
@Getter
@Setter
@NoArgsConstructor
public class Shipment extends BaseClass {

    @Column(name = "shipment_date", nullable = false)
    private LocalDateTime shipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShipmentStatus status;

    @Column(name = "total_weight", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalWeight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<ShipmentItem> shipmentItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<DeliveryStop> deliveryStopList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<TrackingEvent> trackingEventList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<Invoice> invoiceList = new ArrayList<>();
}
