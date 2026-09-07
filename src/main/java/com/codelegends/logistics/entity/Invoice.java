package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "invoice", uniqueConstraints = @UniqueConstraint(columnNames = {"shipment_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends BaseClass {

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
