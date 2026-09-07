package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for invoice resources.
 */
// Registers this class as the Spring service for invoice workflows.
@Service
public class InvoiceService extends CrudService<Invoice, InvoiceDTO> {
    /** Injects persistence access and shared rule validation for Invoice resources. */
    public InvoiceService(InvoiceRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Invoice.class);
    }

    @Override
    /** Creates a new Invoice entity instance for create requests. */
    protected Invoice newEntity() {
        return new Invoice();
    }

    @Override
    /** Converts the persisted Invoice entity to its DTO representation. */
    protected InvoiceDTO toDTO(Invoice entity) {
        return InvoiceDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Invoice entity. */
    protected void copy(InvoiceDTO dto, Invoice entity) {
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        entity.setIssuedDate(dto.getIssuedDate());
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
    }
}
