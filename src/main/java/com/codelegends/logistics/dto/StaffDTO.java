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
public class StaffDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String role;

    @NotBlank
    @Size(max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;

    @NotNull @Positive private Long warehouseId;

    public static StaffDTO convertToDTO(Staff entity) {
        return StaffDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .warehouseId(entity.getWarehouse().getId())
                .build();
    }

    public static List<StaffDTO> convertToDTO(List<Staff> entities) {
        return entities.stream().map(StaffDTO::convertToDTO).toList();
    }
}
