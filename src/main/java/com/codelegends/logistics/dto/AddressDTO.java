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
public class AddressDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String street;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 20)
    private String postalCode;

    @NotBlank
    @Size(max = 100)
    private String country;

    @NotNull @Positive private Long customerId;

    @NotNull @Positive private Long serviceZoneId;

    public static AddressDTO convertToDTO(Address entity) {
        return AddressDTO.builder()
                .id(entity.getId())
                .street(entity.getStreet())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .customerId(entity.getCustomer().getId())
                .serviceZoneId(entity.getServiceZone().getId())
                .build();
    }

    public static List<AddressDTO> convertToDTO(List<Address> entities) {
        return entities.stream().map(AddressDTO::convertToDTO).toList();
    }
}
