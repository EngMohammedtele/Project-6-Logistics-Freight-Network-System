package com.codelegends.logistics.entity;

/**
 * Defines whether a driver or vehicle can currently be assigned to route work.
 */
public enum Availability {
    // Indicates the resource can be assigned to route work.
    AVAILABLE,
    ASSIGNED,
    MAINTENANCE
}
