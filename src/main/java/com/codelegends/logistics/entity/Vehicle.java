package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/**
 * Represents a carrier vehicle that can be assigned to routes.
 */
// Registers Vehicle as a persistent JPA entity.
@Entity
// Defines the database table metadata used for carrier vehicle rows.
@Table(name = "vehicle", uniqueConstraints = @UniqueConstraint(columnNames = {"plate_number"}))
// Generates read accessors for persisted carrier vehicle properties.
@Getter
@Setter
@NoArgsConstructor
public class Vehicle extends BaseClass {

    @Column(name = "plate_number", nullable = false, length = 30)
    /** Stores the vehicle plate number. */
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    /** Stores the enum classification for this resource. */
    private VehicleType type;

    @Column(name = "capacity_kg", nullable = false, precision = 12, scale = 2)
    /** Stores vehicle capacity in kilograms. */
    private BigDecimal capacityKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private Availability status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    /** Defines the carrier relationship used by this logistics record. */
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    /** Defines the route that owns this delivery stop. */
    /** Maintains routes assigned to the driver or vehicle. */
    private List<Route> routeList = new ArrayList<>();
}
