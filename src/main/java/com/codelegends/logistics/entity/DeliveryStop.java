package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(
        name = "delivery_stop",
        uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "stop_sequence"}))
@Getter
@Setter
@NoArgsConstructor
public class DeliveryStop extends BaseClass {

    @Column(name = "stop_sequence", nullable = false)
    private Integer sequence;

    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StopStatus status;

    @Column(name = "eta", nullable = false)
    private LocalDateTime eta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;
}
