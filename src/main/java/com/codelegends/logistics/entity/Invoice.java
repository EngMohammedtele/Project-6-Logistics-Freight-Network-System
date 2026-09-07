package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/**
 * Represents a billing record generated for a delivered shipment.
 */
// Registers Invoice as a persistent JPA entity.
@Entity
// Defines the database table metadata used for shipment invoice rows.
@Table(name = "invoice", uniqueConstraints = @UniqueConstraint(columnNames = {"shipment_id"}))
// Generates read accessors for persisted shipment invoice properties.
@Getter
// Generates write accessors used by JPA and service mapping for shipment invoice.
@Setter
@NoArgsConstructor
public class Invoice extends BaseClass {

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    /** Stores the invoice amount with currency-scale precision. */
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private InvoiceStatus status;

    @Column(name = "issued_date", nullable = false)
    /** Stores the timestamp when an invoice was issued. */
    private LocalDateTime issuedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    /** Defines the shipment associated with this record. */
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    /** Defines the customer relationship used by this logistics record. */
    private Customer customer;
}
