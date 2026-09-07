package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a staff member assigned to a warehouse.
 */
@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
public class Staff extends BaseClass {

    @Column(name = "name", nullable = false, length = 100)
    /** Stores the display name for the resource. */
    private String name;

    @Column(name = "role", nullable = false, length = 100)
    /** Stores the staff role within the warehouse. */
    private String role;

    @Column(name = "phone_number", nullable = false, length = 20)
    /** Accepts the phone number during write operations while keeping it out of responses. */
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    /** Defines the warehouse relationship used by this logistics record. */
    private Warehouse warehouse;
}
