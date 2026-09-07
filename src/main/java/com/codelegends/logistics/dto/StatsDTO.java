package com.codelegends.logistics.dto;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Carries aggregate counts and totals for warehouse, carrier, or customer reporting responses.
 */
@Builder
public record StatsDTO(
        Long id,
        Long activeShipments,
        Long inventoryUnits,
        Long vehicles,
        Long drivers,
        Long activeRoutes,
        BigDecimal totalInvoiced) {}
