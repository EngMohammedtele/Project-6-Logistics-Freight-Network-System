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
public class DriverDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String licenseNumber;

    @NotBlank
    @Size(max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;

    @NotNull private Availability status;

    @NotNull @Positive private Long carrierId;

    public static DriverDTO convertToDTO(Driver entity) {
        return DriverDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier().getId())
                .build();
    }

    public static List<DriverDTO> convertToDTO(List<Driver> entities) {
        return entities.stream().map(DriverDTO::convertToDTO).toList();
    }
}
