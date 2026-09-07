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
// Specializes the shared CRUD workflow for Invoice entities and DTOs.
public class InvoiceService extends CrudService<Invoice, InvoiceDTO> {
    /** Injects persistence access and shared rule validation for Invoice resources. */
    public InvoiceService(InvoiceRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Invoice.class);
    }

    @Override
    /** Creates a new Invoice entity instance for create requests. */
    protected Invoice newEntity() {
        // Returns a blank Invoice instance that the copy method will populate.
        return new Invoice();
    }

    @Override
    /** Converts the persisted Invoice entity to its DTO representation. */
    protected InvoiceDTO toDTO(Invoice entity) {
        // Reuses the DTO mapper to expose persisted invoice values to callers.
        return InvoiceDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Invoice entity. */
    // Maps request DTO fields onto the mutable Invoice entity.
    protected void copy(InvoiceDTO dto, Invoice entity) {
        // Copies the invoice amount into the billing entity.
        entity.setAmount(dto.getAmount());
        // Copies the invoice payment status.
        entity.setStatus(dto.getStatus());
        // Copies the issue timestamp for the invoice.
        entity.setIssuedDate(dto.getIssuedDate());
        // Resolves the delivered shipment being billed.
        entity.setShipment(access.get(Shipment.class, dto.getShipmentId()));
        // Resolves the customer responsible for payment.
        entity.setCustomer(access.get(Customer.class, dto.getCustomerId()));
    }
}
