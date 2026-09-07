package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a timestamped shipment tracking update.
 */
// Registers TrackingEvent as a persistent JPA entity.
@Entity
// Defines the database table metadata used for shipment tracking event rows.
@Table(name = "tracking_event")
// Generates read accessors for persisted shipment tracking event properties.
@Getter
@Setter
@NoArgsConstructor
public class TrackingEvent extends BaseClass {

    @Column(name = "event_time", nullable = false)
    /** Stores when the tracking event occurred. */
    private LocalDateTime eventTime;

    @Column(name = "location", nullable = false, length = 200)
    /** Stores where the tracking event was recorded. */
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    /** Stores the current workflow status for the resource. */
    private ShipmentStatus status;

    @Column(name = "note", nullable = false, length = 500)
    /** Stores the human-readable tracking event note. */
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    /** Defines the shipment associated with this record. */
    private Shipment shipment;
}
