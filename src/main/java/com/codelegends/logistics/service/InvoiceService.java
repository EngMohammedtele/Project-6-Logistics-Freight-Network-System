package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService extends CrudService<Invoice, InvoiceDTO> {
    public InvoiceService(InvoiceRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Invoice.class);
    }

    @Override
    protected Invoice newEntity() {
        return new Invoice();
    }

    @Override
    protected InvoiceDTO toDTO(Invoice entity) {
        return InvoiceDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(InvoiceDTO dto, Invoice entity) {
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        entity.setIssuedDate(dto.getIssuedDate());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
    }
}
