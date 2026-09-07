package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "tracking_event")
@Getter
@Setter
@NoArgsConstructor
public class TrackingEvent extends BaseClass {

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShipmentStatus status;

    @Column(name = "note", nullable = false, length = 500)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;
}
