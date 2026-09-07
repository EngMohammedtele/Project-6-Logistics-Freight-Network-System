package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.ShipmentStatus;
import com.codelegends.logistics.service.QueryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/queries")
@RequiredArgsConstructor
public class QueryController {
    private final QueryService service;

    @GetMapping("/shipments")
    public List<ShipmentDTO> shipments(@RequestParam ShipmentStatus status) {
        return service.status(status);
    }

    @GetMapping("/inventory")
    public List<InventoryItemDTO> inventory(@RequestParam Integer threshold) {
        return service.below(threshold);
    }

    @GetMapping("/driver-routes")
    public List<RouteDTO> routes(@RequestParam Long driverId, @RequestParam LocalDate date) {
        return service.driver(driverId, date);
    }

    @GetMapping("/available-vehicles")
    public List<VehicleDTO> available() {
        return service.available();
    }

    @GetMapping("/customers/{id}/shipments")
    public List<ShipmentDTO> history(@PathVariable Long id) {
        return service.history(id);
    }

    @GetMapping("/customers/{id}/unpaid-invoices")
    public List<InvoiceDTO> unpaid(@PathVariable Long id) {
        return service.unpaid(id);
    }

    @GetMapping("/warehouses/{id}/stats")
    public StatsDTO warehouse(@PathVariable Long id) {
        return service.warehouse(id);
    }

    @GetMapping("/carriers/{id}/stats")
    public StatsDTO carrier(@PathVariable Long id) {
        return service.carrier(id);
    }

    @GetMapping("/customers/{id}/stats")
    public StatsDTO customer(@PathVariable Long id) {
        return service.customer(id);
    }
}
