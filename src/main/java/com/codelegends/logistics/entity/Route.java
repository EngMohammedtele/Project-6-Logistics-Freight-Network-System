package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a planned delivery route assigned to a vehicle and driver.
 */
// Registers Route as a persistent JPA entity.
@Entity
// Defines the database table metadata used for delivery route rows.
@Table(name = "route")
// Generates read accessors for persisted delivery route properties.
@Getter
// Generates write accessors used by JPA and service mapping for delivery route.
@Setter
// Provides the default constructor required by JPA for delivery route hydration.
@NoArgsConstructor
public class Route extends BaseClass {

    @Column(name = "route_date", nullable = false)
    /** Stores the calendar date on which the route is planned. */
    private LocalDate routeDate;

    @Column(name = "origin", nullable = false, length = 200)
    /** Stores the route starting location. */
    private String origin;

    @Column(name = "destination", nullable = false, length = 200)
    /** Stores the route destination. */
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private RouteStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    /** Defines the vehicle assigned to the route. */
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    /** Defines the driver assigned to the route. */
    private Driver driver;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "route")
    /** Maintains delivery stops associated with the route or shipment. */
    private List<DeliveryStop> deliveryStopList = new ArrayList<>();
}
