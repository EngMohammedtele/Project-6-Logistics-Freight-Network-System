package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

// Imports Query so the repository can declare explicit JPQL statements.
import org.springframework.data.jpa.repository.Query;
// Imports Param to bind method arguments into named JPQL parameters.
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for invoice entities.
 */
// Binds Invoice persistence to the shared active-record repository contract.
// Uses Invoice as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for invoice database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface InvoiceRepository extends ActiveRepository<Invoice> {

    // Executes a custom JPQL query instead of relying only on method-name derivation.
    // Filters active invoices by both customer ownership and payment status.
    @Query(
            "select i from Invoice i where i.isActive=true and i.customer.id=:customerId and"
                + " i.status=:status")
    /** Finds active invoices for a customer that match the requested payment status. */
    // Returns matching invoice records for customer billing views.
    // Uses the customer identifier to restrict invoices to one account.
    // Uses the status argument so callers can request paid or unpaid invoices.
    // Returns a list because one customer can have multiple invoices in the same state.
    List<Invoice> unpaid(
            @Param("customerId") Long customerId, @Param("status") InvoiceStatus status);
}
