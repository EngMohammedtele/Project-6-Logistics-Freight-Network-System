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
public class InvoiceDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    @NotNull private InvoiceStatus status;

    @NotNull @PastOrPresent private LocalDateTime issuedDate;

    @NotNull @Positive private Long shipmentId;

    @NotNull @Positive private Long customerId;

    public static InvoiceDTO convertToDTO(Invoice entity) {
        return InvoiceDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .issuedDate(entity.getIssuedDate())
                .shipmentId(entity.getShipment().getId())
                .customerId(entity.getCustomer().getId())
                .build();
    }

    public static List<InvoiceDTO> convertToDTO(List<Invoice> entities) {
        return entities.stream().map(InvoiceDTO::convertToDTO).toList();
    }
}
