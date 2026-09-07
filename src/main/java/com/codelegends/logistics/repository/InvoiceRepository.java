package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for invoice entities.
 */
// Binds Invoice persistence to the shared active-record repository contract.
// Uses Invoice as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for invoice database access.
public interface InvoiceRepository extends ActiveRepository<Invoice> {

    @Query(
            "select i from Invoice i where i.isActive=true and i.customer.id=:customerId and"
                + " i.status=:status")
    /** Finds active invoices for a customer that match the requested payment status. */
    List<Invoice> unpaid(
            @Param("customerId") Long customerId, @Param("status") InvoiceStatus status);
}
