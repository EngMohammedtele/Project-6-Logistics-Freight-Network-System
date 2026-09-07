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
public class InventoryItemDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull @PositiveOrZero private Integer quantity;

    @NotBlank
    @Size(max = 100)
    private String shelfLocation;

    @NotNull @Positive private Long warehouseId;

    @NotNull @Positive private Long productId;

    public static InventoryItemDTO convertToDTO(InventoryItem entity) {
        return InventoryItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .shelfLocation(entity.getShelfLocation())
                .warehouseId(entity.getWarehouse().getId())
                .productId(entity.getProduct().getId())
                .build();
    }

    public static List<InventoryItemDTO> convertToDTO(List<InventoryItem> entities) {
        return entities.stream().map(InventoryItemDTO::convertToDTO).toList();
    }
}
