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
public class WarehouseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String location;

    @NotNull @Positive private Integer capacity;

    public static WarehouseDTO convertToDTO(Warehouse entity) {
        return WarehouseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .capacity(entity.getCapacity())
                .build();
    }

    public static List<WarehouseDTO> convertToDTO(List<Warehouse> entities) {
        return entities.stream().map(WarehouseDTO::convertToDTO).toList();
    }
}
