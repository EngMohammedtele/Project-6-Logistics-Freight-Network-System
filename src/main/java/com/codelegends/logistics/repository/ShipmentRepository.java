package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

// Imports Query so the repository can declare explicit JPQL statements.
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for shipment entities.
 */
// Binds Shipment persistence to the shared active-record repository contract.
// Uses Shipment as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for shipment database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface ShipmentRepository extends ActiveRepository<Shipment> {

    @Query("select s from Shipment s where s.isActive=true and s.status=:status")
    /** Finds active shipments currently in the requested status. */
    List<Shipment> byStatus(@Param("status") ShipmentStatus status);

    @Query("select s from Shipment s where s.isActive=true and s.customer.id=:customerId")
    /** Finds active shipment history for the supplied customer. */
    List<Shipment> history(@Param("customerId") Long customerId);
}
