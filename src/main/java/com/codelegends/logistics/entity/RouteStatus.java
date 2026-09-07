package com.codelegends.logistics.entity;

/**
 * Tracks the lifecycle state of a delivery route.
 */
public enum RouteStatus {
    // Indicates the route has been created but dispatch has not started.
    PLANNED,
    IN_PROGRESS,
    COMPLETED
}
