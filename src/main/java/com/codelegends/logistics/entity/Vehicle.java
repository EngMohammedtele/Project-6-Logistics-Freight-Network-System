package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "vehicle", uniqueConstraints = @UniqueConstraint(columnNames = {"plate_number"}))
@Getter
@Setter
@NoArgsConstructor
public class Vehicle extends BaseClass {

    @Column(name = "plate_number", nullable = false, length = 30)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @Column(name = "capacity_kg", nullable = false, precision = 12, scale = 2)
    private BigDecimal capacityKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Availability status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Route> routeList = new ArrayList<>();
}
