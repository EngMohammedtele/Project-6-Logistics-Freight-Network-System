package com.codelegends.logistics.dto;

import com.codelegends.logistics.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentItemDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull @Positive private Integer quantity;

    @NotNull @Positive private Long shipmentId;

    @NotNull @Positive private Long productId;

    public static ShipmentItemDTO convertToDTO(ShipmentItem entity) {
        return ShipmentItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .shipmentId(entity.getShipment().getId())
                .productId(entity.getProduct().getId())
                .build();
    }

    public static List<ShipmentItemDTO> convertToDTO(List<ShipmentItem> entities) {
        return entities.stream().map(ShipmentItemDTO::convertToDTO).toList();
    }
}
