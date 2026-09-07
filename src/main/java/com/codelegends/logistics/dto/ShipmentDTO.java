package com.codelegends.logistics.dto;

import com.codelegends.logistics.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull @PastOrPresent private LocalDateTime shipmentDate;

    @NotNull private ShipmentStatus status;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalWeight;

    @NotNull @Positive private Long warehouseId;

    @NotNull @Positive private Long customerId;

    @NotNull @Positive private Long carrierId;

    public static ShipmentDTO convertToDTO(Shipment entity) {
        return ShipmentDTO.builder()
                .id(entity.getId())
                .shipmentDate(entity.getShipmentDate())
                .status(entity.getStatus())
                .totalWeight(entity.getTotalWeight())
                .warehouseId(entity.getWarehouse().getId())
                .customerId(entity.getCustomer().getId())
                .carrierId(entity.getCarrier().getId())
                .build();
    }

    public static List<ShipmentDTO> convertToDTO(List<Shipment> entities) {
        return entities.stream().map(ShipmentDTO::convertToDTO).toList();
    }
}
