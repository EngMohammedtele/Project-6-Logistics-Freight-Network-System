package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a logistics carrier that owns vehicles, drivers, and shipments.
 */
// Registers Carrier as a persistent JPA entity.
@Entity
// Defines the database table metadata used for transport carrier rows.
@Table(name = "carrier")
// Generates read accessors for persisted transport carrier properties.
@Getter
// Generates write accessors used by JPA and service mapping for transport carrier.
@Setter
// Provides the default constructor required by JPA for transport carrier hydration.
@NoArgsConstructor
public class Carrier extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "contact_email", nullable = false, length = 150)
    /** Accepts the carrier contact email during write operations. */
    private String contactEmail;

    @Column(name = "phone_number", nullable = false, length = 20)
    /** Accepts the phone number during write operations while keeping it out of responses. */
    private String phoneNumber;

    @Column(name = "country", nullable = false, length = 100)
    /** Stores the country associated with the resource. */
    private String country;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    /** Defines the vehicle assigned to the route. */
    /** Maintains vehicles owned by the carrier. */
    private List<Vehicle> vehicleList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    /** Defines the driver assigned to the route. */
    /** Maintains drivers owned by the carrier. */
    private List<Driver> driverList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    /** Defines the shipment associated with this record. */
    /** Maintains shipments associated with the customer, carrier, or warehouse. */
    private List<Shipment> shipmentList = new ArrayList<>();
}
