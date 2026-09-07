package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "driver", uniqueConstraints = @UniqueConstraint(columnNames = {"license_number"}))
@Getter
@Setter
@NoArgsConstructor
public class Driver extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "license_number", nullable = false, length = 50)
    private String licenseNumber;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Availability status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Route> routeList = new ArrayList<>();
}
