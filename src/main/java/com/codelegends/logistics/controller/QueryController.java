package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.ShipmentStatus;
import com.codelegends.logistics.service.QueryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Exposes read-only query endpoints for operational logistics reporting and lookups.
 */
@RestController
@RequestMapping("/api/queries")
@RequiredArgsConstructor
public class QueryController {
    /** Service that provides read-only operational query results. */
    private final QueryService service;

    @GetMapping("/shipments")
    /** Returns active shipments filtered by status. */
    public List<ShipmentDTO> shipments(@RequestParam ShipmentStatus status) {
        return service.status(status);
    }

    @GetMapping("/inventory")
    /** Returns inventory items below the supplied quantity threshold. */
    public List<InventoryItemDTO> inventory(@RequestParam Integer threshold) {
        return service.below(threshold);
    }

    @GetMapping("/driver-routes")
    /** Returns routes for a driver on a specific date. */
    public List<RouteDTO> routes(@RequestParam Long driverId, @RequestParam LocalDate date) {
        return service.driver(driverId, date);
    }

    @GetMapping("/available-vehicles")
    /** Returns vehicles that are currently available for assignment. */
    public List<VehicleDTO> available() {
        return service.available();
    }

    @GetMapping("/customers/{id}/shipments")
    /** Returns shipment history for the requested customer. */
    public List<ShipmentDTO> history(@PathVariable Long id) {
        return service.history(id);
    }

    @GetMapping("/customers/{id}/unpaid-invoices")
    /** Returns unpaid invoices for the requested customer. */
    public List<InvoiceDTO> unpaid(@PathVariable Long id) {
        return service.unpaid(id);
    }

    @GetMapping("/warehouses/{id}/stats")
    /** Returns aggregate warehouse activity statistics. */
    public StatsDTO warehouse(@PathVariable Long id) {
        return service.warehouse(id);
    }

    @GetMapping("/carriers/{id}/stats")
    /** Returns aggregate carrier activity statistics. */
    public StatsDTO carrier(@PathVariable Long id) {
        return service.carrier(id);
    }

    @GetMapping("/customers/{id}/stats")
    /** Returns aggregate customer billing and shipment statistics. */
    public StatsDTO customer(@PathVariable Long id) {
        return service.customer(id);
    }
}
