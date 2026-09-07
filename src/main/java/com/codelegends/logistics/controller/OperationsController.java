package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.service.OperationsService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

/**
 * Exposes workflow-oriented logistics operations that coordinate shipments, routes, stops, tracking events, and invoices.
 */
@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationsController {
    /** Service that coordinates the multi-step logistics workflows. */
    private final OperationsService service;

    @PostMapping("/shipments")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    /** Creates a shipment with line items through the operations workflow. */
    public ShipmentDTO create(@Valid @RequestBody Operations.CreateShipment dto) {
        return service.createShipment(dto);
    }

    @PutMapping("/shipments/{id}/carrier")
    /** Reassigns the carrier for a shipment that has not entered route planning. */
    public ShipmentDTO assign(
            @PathVariable Long id, @Valid @RequestBody Operations.AssignCarrier dto) {
        return service.assign(id, dto);
    }

    @PostMapping("/routes")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    /** Builds a planned route with an available vehicle and driver. */
    public RouteDTO route(@Valid @RequestBody Operations.BuildRoute dto) {
        return service.build(dto);
    }

    @PostMapping("/routes/{id}/stops")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    /** Adds a delivery stop to an existing route. */
    public DeliveryStopDTO stop(@PathVariable Long id, @Valid @RequestBody Operations.Stop dto) {
        return service.addStop(id, dto);
    }

    @PostMapping("/shipments/{id}/tracking")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    /** Records a tracking event for the requested shipment. */
    public TrackingEventDTO track(@PathVariable Long id, @Valid @RequestBody Operations.Track dto) {
        return service.track(id, dto);
    }

    @PutMapping("/stops/{id}/complete")
    /** Completes a stop and updates related shipment and route state. */
    public DeliveryStopDTO complete(
            @PathVariable Long id, @Valid @RequestBody Operations.Complete dto) {
        return service.complete(id);
    }

    @PostMapping("/shipments/{id}/invoice")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    /** Generates an invoice for a delivered shipment. */
    public InvoiceDTO invoice(
            @PathVariable Long id, @Valid @RequestBody Operations.GenerateInvoice dto) {
        return service.invoice(id, dto);
    }
}
