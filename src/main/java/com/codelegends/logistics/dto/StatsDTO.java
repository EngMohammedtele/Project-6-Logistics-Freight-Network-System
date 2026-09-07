package com.codelegends.logistics.dto;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Carries aggregate counts and totals for warehouse, carrier, or customer reporting responses.
 */
// Supports concise creation of aggregate statistics responses.
@Builder
public record StatsDTO(
        /** Identifies the warehouse, carrier, or customer represented by the statistics. */
        Long id,
        /** Counts active shipments included in the statistics response. */
        Long activeShipments,
        /** Sums active inventory units where the queried resource owns warehouse stock. */
        Long inventoryUnits,
        /** Counts active vehicles associated with a carrier. */
        Long vehicles,
        /** Counts active drivers associated with a carrier. */
        Long drivers,
        /** Counts active routes associated with the queried carrier. */
        Long activeRoutes,
        /** Sums invoice amounts associated with the queried resource. */
        BigDecimal totalInvoiced) {}
