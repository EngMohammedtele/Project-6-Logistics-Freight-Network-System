package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

/**
 * Represents a serviceable region and its base delivery rate.
 */
@Entity
@Table(name = "service_zone")
@Getter
@Setter
@NoArgsConstructor
public class ServiceZone extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "region", nullable = false, length = 100)
    /** Stores the service region covered by the zone. */
    private String region;

    @Column(name = "base_rate", nullable = false, precision = 12, scale = 2)
    /** Stores the base service rate for the zone. */
    private BigDecimal baseRate;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "serviceZone")
    /** Stores the delivery address for the stop. */
    /** Maintains the addresses owned by the customer or service zone. */
    private List<Address> addressList = new ArrayList<>();
}
