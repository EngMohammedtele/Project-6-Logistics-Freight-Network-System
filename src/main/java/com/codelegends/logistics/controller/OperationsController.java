package com.codelegends.logistics.controller;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.service.OperationsService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationsController {
    private final OperationsService service;

    @PostMapping("/shipments")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ShipmentDTO create(@Valid @RequestBody Operations.CreateShipment dto) {
        return service.createShipment(dto);
    }

    @PutMapping("/shipments/{id}/carrier")
    public ShipmentDTO assign(
            @PathVariable Long id, @Valid @RequestBody Operations.AssignCarrier dto) {
        return service.assign(id, dto);
    }

    @PostMapping("/routes")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public RouteDTO route(@Valid @RequestBody Operations.BuildRoute dto) {
        return service.build(dto);
    }

    @PostMapping("/routes/{id}/stops")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public DeliveryStopDTO stop(@PathVariable Long id, @Valid @RequestBody Operations.Stop dto) {
        return service.addStop(id, dto);
    }

    @PostMapping("/shipments/{id}/tracking")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public TrackingEventDTO track(@PathVariable Long id, @Valid @RequestBody Operations.Track dto) {
        return service.track(id, dto);
    }

    @PutMapping("/stops/{id}/complete")
    public DeliveryStopDTO complete(
            @PathVariable Long id, @Valid @RequestBody Operations.Complete dto) {
        return service.complete(id);
    }

    @PostMapping("/shipments/{id}/invoice")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public InvoiceDTO invoice(
            @PathVariable Long id, @Valid @RequestBody Operations.GenerateInvoice dto) {
        return service.invoice(id, dto);
    }
}
