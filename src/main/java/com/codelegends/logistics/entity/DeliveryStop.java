package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a scheduled stop on a route for a shipment delivery.
 */
// Registers DeliveryStop as a persistent JPA entity.
@Entity
// Defines the database table metadata used for delivery stop rows.
@Table(
        name = "delivery_stop",
        uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "stop_sequence"}))
@Getter
@Setter
@NoArgsConstructor
public class DeliveryStop extends BaseClass {

    @Column(name = "stop_sequence", nullable = false)
    /** Stores the route stop order used for delivery sequencing. */
    private Integer sequence;

    @Column(name = "address", nullable = false, length = 300)
    /** Stores the delivery address for the stop. */
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private StopStatus status;

    @Column(name = "eta", nullable = false)
    /** Stores the expected arrival time for a delivery stop. */
    private LocalDateTime eta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    /** Defines the route that owns this delivery stop. */
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    /** Defines the shipment associated with this record. */
    private Shipment shipment;
}
