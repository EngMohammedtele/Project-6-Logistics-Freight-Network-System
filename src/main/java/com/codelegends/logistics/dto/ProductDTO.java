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
public class ProductDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String sku;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal weightKg;

    @NotBlank
    @Size(max = 100)
    private String category;

    public static ProductDTO convertToDTO(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sku(entity.getSku())
                .weightKg(entity.getWeightKg())
                .category(entity.getCategory())
                .build();
    }

    public static List<ProductDTO> convertToDTO(List<Product> entities) {
        return entities.stream().map(ProductDTO::convertToDTO).toList();
    }
}
