package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
public class Route extends BaseClass {

    @Column(name = "route_date", nullable = false)
    private LocalDate routeDate;

    @Column(name = "origin", nullable = false, length = 200)
    private String origin;

    @Column(name = "destination", nullable = false, length = 200)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RouteStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "route")
    private List<DeliveryStop> deliveryStopList = new ArrayList<>();
}
