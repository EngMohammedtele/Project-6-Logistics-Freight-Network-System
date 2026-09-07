package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a customer that can own addresses, shipments, and invoices.
 */
// Registers Customer as a persistent JPA entity.
@Entity
// Defines the database table metadata used for customer account rows.
@Table(name = "customer")
// Generates read accessors for persisted customer account properties.
@Getter
// Generates write accessors used by JPA and service mapping for customer account.
@Setter
@NoArgsConstructor
public class Customer extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "email", nullable = false, length = 150)
    /** Accepts the customer email during write operations. */
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    /** Accepts the phone number during write operations while keeping it out of responses. */
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    /** Stores the enum classification for this resource. */
    private CustomerType type;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    /** Stores the delivery address for the stop. */
    /** Maintains the addresses owned by the customer or service zone. */
    private List<Address> addressList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    /** Defines the shipment associated with this record. */
    /** Maintains shipments associated with the customer, carrier, or warehouse. */
    private List<Shipment> shipmentList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    /** Maintains invoices associated with the customer or shipment. */
    private List<Invoice> invoiceList = new ArrayList<>();
}
