package com.codelegends.logistics.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.*;
import java.util.*;

/**
 * Represents a product line item included in a shipment.
 */
// Registers ShipmentItem as a persistent JPA entity.
@Entity
// Defines the database table metadata used for shipment line item rows.
@Table(name = "shipment_item")
@Getter
@Setter
@NoArgsConstructor
public class ShipmentItem extends BaseClass {

    @Column(name = "quantity", nullable = false)
    /** Stores the item or inventory quantity. */
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipment_id", nullable = false)
    /** Defines the shipment associated with this record. */
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    /** Defines the product associated with this inventory or shipment item. */
    private Product product;
}
