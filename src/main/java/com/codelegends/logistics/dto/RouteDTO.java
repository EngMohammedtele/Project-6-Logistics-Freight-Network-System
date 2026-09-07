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
public class RouteDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull private LocalDate routeDate;

    @NotBlank
    @Size(max = 200)
    private String origin;

    @NotBlank
    @Size(max = 200)
    private String destination;

    @NotNull private RouteStatus status;

    @NotNull @Positive private Long vehicleId;

    @NotNull @Positive private Long driverId;

    public static RouteDTO convertToDTO(Route entity) {
        return RouteDTO.builder()
                .id(entity.getId())
                .routeDate(entity.getRouteDate())
                .origin(entity.getOrigin())
                .destination(entity.getDestination())
                .status(entity.getStatus())
                .vehicleId(entity.getVehicle().getId())
                .driverId(entity.getDriver().getId())
                .build();
    }

    public static List<RouteDTO> convertToDTO(List<Route> entities) {
        return entities.stream().map(RouteDTO::convertToDTO).toList();
    }
}
