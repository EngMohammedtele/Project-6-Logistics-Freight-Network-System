package com.codelegends.logistics.dto;

import com.codelegends.logistics.entity.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

/**
 * Groups request payload records for workflow-oriented logistics operations.
 */
public final class Operations {
    private Operations() {}

    /** Describes one product and quantity requested for a shipment. */
    public record Line(@NotNull @Positive Long productId, @Positive int quantity) {}

    /** Request body for creating a shipment with one or more line items. */
    public record CreateShipment(
            @NotNull @Positive Long warehouseId,
            @NotNull @Positive Long customerId,
            @NotNull @Positive Long carrierId,
            @NotEmpty List<@Valid Line> items) {}

    /** Request body for assigning a new carrier to an eligible shipment. */
    public record AssignCarrier(@NotNull @Positive Long carrierId) {}

    /** Request body for creating a planned route with assigned resources. */
    public record BuildRoute(
            @NotNull @Positive Long vehicleId,
            @NotNull @Positive Long driverId,
            @NotNull @FutureOrPresent LocalDate routeDate,
            @NotBlank @Size(max = 200) String origin,
            @NotBlank @Size(max = 200) String destination) {}

    /** Request body for adding a pending delivery stop to a route. */
    public record Stop(
            @NotNull @Positive Long shipmentId,
            @Positive int sequence,
            @NotBlank @Size(max = 300) String address,
            @NotNull @Future LocalDateTime eta) {}

    /** Request body for recording a shipment tracking update. */
    public record Track(
            @NotNull @PastOrPresent LocalDateTime eventTime,
            @NotBlank @Size(max = 200) String location,
            @NotNull ShipmentStatus status,
            @NotBlank @Size(max = 500) String note) {}

    /** Request body used by stop completion calls to satisfy validation. */
    public record Complete(@AssertTrue boolean completed) {}

    /** Request body for generating an invoice amount for a delivered shipment. */
    public record GenerateInvoice(
            @NotNull @DecimalMin("0.01") @Digits(integer = 10, fraction = 2) BigDecimal amount) {}
}
