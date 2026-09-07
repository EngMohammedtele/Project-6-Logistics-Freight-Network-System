package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a customer address assigned to a service zone.
 */
// Registers Address as a persistent JPA entity.
@Entity
// Defines the database table metadata used for customer address rows.
@Table(name = "address")
// Generates read accessors for persisted customer address properties.
@Getter
// Generates write accessors used by JPA and service mapping for customer address.
@Setter
// Provides the default constructor required by JPA for customer address hydration.
@NoArgsConstructor
public class Address extends BaseClass {

    @Column(name = "street", nullable = false, length = 200)
    /** Stores the street address line used for customer location records. */
    private String street;

    @Column(name = "city", nullable = false, length = 100)
    /** Stores the city portion of the address. */
    private String city;

    @Column(name = "postal_code", nullable = false, length = 20)
    /** Stores the postal code portion of the address. */
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    /** Stores the country associated with the resource. */
    private String country;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    /** Defines the customer relationship used by this logistics record. */
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serviceZone_id", nullable = false)
    /** Defines the service-zone relationship for the address. */
    private ServiceZone serviceZone;
}
