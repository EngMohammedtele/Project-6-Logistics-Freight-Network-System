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
public class TrackingEventDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull @PastOrPresent private LocalDateTime eventTime;

    @NotBlank
    @Size(max = 200)
    private String location;

    @NotNull private ShipmentStatus status;

    @NotBlank
    @Size(max = 500)
    private String note;

    @NotNull @Positive private Long shipmentId;

    public static TrackingEventDTO convertToDTO(TrackingEvent entity) {
        return TrackingEventDTO.builder()
                .id(entity.getId())
                .eventTime(entity.getEventTime())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .note(entity.getNote())
                .shipmentId(entity.getShipment().getId())
                .build();
    }

    public static List<TrackingEventDTO> convertToDTO(List<TrackingEvent> entities) {
        return entities.stream().map(TrackingEventDTO::convertToDTO).toList();
    }
}
