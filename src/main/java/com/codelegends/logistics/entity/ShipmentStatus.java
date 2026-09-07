package com.codelegends.logistics.entity;

/**
 * Tracks the lifecycle state of a shipment as tracking events are recorded.
 */
public enum ShipmentStatus {
    // Indicates the shipment record exists before pickup begins.
    CREATED,
    PICKED_UP,
    IN_TRANSIT,
    // Indicates the shipment has reached its final delivery state.
    DELIVERED
}
