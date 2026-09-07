package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/**
 * Represents a customer shipment handled by a warehouse and carrier.
 */
@Entity
@Table(name = "shipment")
@Getter
@Setter
@NoArgsConstructor
public class Shipment extends BaseClass {

    @Column(name = "shipment_date", nullable = false)
    /** Stores when the shipment was created or registered. */
    private LocalDateTime shipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private ShipmentStatus status;

    @Column(name = "total_weight", nullable = false, precision = 12, scale = 2)
    /** Stores the shipment weight derived from active shipment items. */
    private BigDecimal totalWeight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    /** Defines the warehouse relationship used by this logistics record. */
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    /** Defines the customer relationship used by this logistics record. */
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    /** Defines the carrier relationship used by this logistics record. */
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    /** Maintains shipment item records for the shipment or product. */
    private List<ShipmentItem> shipmentItemList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    /** Maintains delivery stops associated with the route or shipment. */
    private List<DeliveryStop> deliveryStopList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    /** Maintains tracking events recorded for the shipment. */
    private List<TrackingEvent> trackingEventList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "shipment")
    /** Maintains invoices associated with the customer or shipment. */
    private List<Invoice> invoiceList = new ArrayList<>();
}
