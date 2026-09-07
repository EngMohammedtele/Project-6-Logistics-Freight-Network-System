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
public class VehicleDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String plateNumber;

    @NotNull private VehicleType type;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal capacityKg;

    @NotNull private Availability status;

    @NotNull @Positive private Long carrierId;

    public static VehicleDTO convertToDTO(Vehicle entity) {
        return VehicleDTO.builder()
                .id(entity.getId())
                .plateNumber(entity.getPlateNumber())
                .type(entity.getType())
                .capacityKg(entity.getCapacityKg())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier().getId())
                .build();
    }

    public static List<VehicleDTO> convertToDTO(List<Vehicle> entities) {
        return entities.stream().map(VehicleDTO::convertToDTO).toList();
    }
}
