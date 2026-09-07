package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "carrier")
@Getter
@Setter
@NoArgsConstructor
public class Carrier extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "contact_email", nullable = false, length = 150)
    private String contactEmail;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Vehicle> vehicleList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Driver> driverList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Shipment> shipmentList = new ArrayList<>();
}
