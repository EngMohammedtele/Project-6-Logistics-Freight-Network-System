package com.codelegends.logistics.entity;

/**
 * Tracks the lifecycle state of a shipment as tracking events are recorded.
 */
public enum ShipmentStatus {
    CREATED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED
}
