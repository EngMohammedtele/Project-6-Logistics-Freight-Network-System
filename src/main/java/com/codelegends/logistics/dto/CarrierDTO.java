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
public class CarrierDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 150)
    @Email
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contactEmail;

    @NotBlank
    @Size(max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;

    @NotBlank
    @Size(max = 100)
    private String country;

    public static CarrierDTO convertToDTO(Carrier entity) {
        return CarrierDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .build();
    }

    public static List<CarrierDTO> convertToDTO(List<Carrier> entities) {
        return entities.stream().map(CarrierDTO::convertToDTO).toList();
    }
}
