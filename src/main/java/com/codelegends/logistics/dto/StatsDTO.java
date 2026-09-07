package com.codelegends.logistics.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StatsDTO(
        Long id,
        Long activeShipments,
        Long inventoryUnits,
        Long vehicles,
        Long drivers,
        Long activeRoutes,
        BigDecimal totalInvoiced) {}
