package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "service_zone")
@Getter
@Setter
@NoArgsConstructor
public class ServiceZone extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "base_rate", nullable = false, precision = 12, scale = 2)
    private BigDecimal baseRate;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "serviceZone")
    private List<Address> addressList = new ArrayList<>();
}
