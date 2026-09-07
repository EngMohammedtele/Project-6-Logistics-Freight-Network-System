package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a carrier driver who can be assigned to delivery routes.
 */
// Registers Driver as a persistent JPA entity.
@Entity
// Defines the database table metadata used for carrier driver rows.
@Table(name = "driver", uniqueConstraints = @UniqueConstraint(columnNames = {"license_number"}))
// Generates read accessors for persisted carrier driver properties.
@Getter
@Setter
@NoArgsConstructor
public class Driver extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "license_number", nullable = false, length = 50)
    /** Accepts the driver license number during write operations. */
    private String licenseNumber;

    @Column(name = "phone_number", nullable = false, length = 20)
    /** Accepts the phone number during write operations while keeping it out of responses. */
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private Availability status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    /** Defines the carrier relationship used by this logistics record. */
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "driver")
    /** Defines the route that owns this delivery stop. */
    /** Maintains routes assigned to the driver or vehicle. */
    private List<Route> routeList = new ArrayList<>();
}
