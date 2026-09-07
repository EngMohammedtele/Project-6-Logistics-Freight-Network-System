package com.codelegends.logistics.dto;

import com.codelegends.logistics.entity.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

public final class Operations {
    private Operations() {}

    public record Line(@NotNull @Positive Long productId, @Positive int quantity) {}

    public record CreateShipment(
            @NotNull @Positive Long warehouseId,
            @NotNull @Positive Long customerId,
            @NotNull @Positive Long carrierId,
            @NotEmpty List<@Valid Line> items) {}

    public record AssignCarrier(@NotNull @Positive Long carrierId) {}

    public record BuildRoute(
            @NotNull @Positive Long vehicleId,
            @NotNull @Positive Long driverId,
            @NotNull @FutureOrPresent LocalDate routeDate,
            @NotBlank @Size(max = 200) String origin,
            @NotBlank @Size(max = 200) String destination) {}

    public record Stop(
            @NotNull @Positive Long shipmentId,
            @Positive int sequence,
            @NotBlank @Size(max = 300) String address,
            @NotNull @Future LocalDateTime eta) {}

    public record Track(
            @NotNull @PastOrPresent LocalDateTime eventTime,
            @NotBlank @Size(max = 200) String location,
            @NotNull ShipmentStatus status,
            @NotBlank @Size(max = 500) String note) {}

    public record Complete(@AssertTrue boolean completed) {}

    public record GenerateInvoice(
            @NotNull @DecimalMin("0.01") @Digits(integer = 10, fraction = 2) BigDecimal amount) {}
}
