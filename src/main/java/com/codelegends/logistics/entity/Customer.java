package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CustomerType type;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Address> addressList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Shipment> shipmentList = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoiceList = new ArrayList<>();
}
