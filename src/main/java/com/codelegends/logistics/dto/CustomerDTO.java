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
public class CustomerDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 150)
    @Email
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @NotBlank
    @Size(max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String phoneNumber;

    @NotNull private CustomerType type;

    public static CustomerDTO convertToDTO(Customer entity) {
        return CustomerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .build();
    }

    public static List<CustomerDTO> convertToDTO(List<Customer> entities) {
        return entities.stream().map(CustomerDTO::convertToDTO).toList();
    }
}
