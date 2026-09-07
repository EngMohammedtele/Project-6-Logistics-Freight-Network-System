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
public class DeliveryStopDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull @Positive private Integer sequence;

    @NotBlank
    @Size(max = 300)
    private String address;

    @NotNull private StopStatus status;

    @NotNull private LocalDateTime eta;

    @NotNull @Positive private Long routeId;

    @NotNull @Positive private Long shipmentId;

    public static DeliveryStopDTO convertToDTO(DeliveryStop entity) {
        return DeliveryStopDTO.builder()
                .id(entity.getId())
                .sequence(entity.getSequence())
                .address(entity.getAddress())
                .status(entity.getStatus())
                .eta(entity.getEta())
                .routeId(entity.getRoute().getId())
                .shipmentId(entity.getShipment().getId())
                .build();
    }

    public static List<DeliveryStopDTO> convertToDTO(List<DeliveryStop> entities) {
        return entities.stream().map(DeliveryStopDTO::convertToDTO).toList();
    }
}
