package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

// Imports Query so the repository can declare explicit JPQL statements.
import org.springframework.data.jpa.repository.Query;
// Imports Param to bind method arguments into named JPQL parameters.
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for vehicle entities.
 */
// Binds Vehicle persistence to the shared active-record repository contract.
// Uses Vehicle as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for vehicle database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface VehicleRepository extends ActiveRepository<Vehicle> {

    // Executes a custom JPQL query instead of relying only on method-name derivation.
    // Filters active vehicles by availability status.
    @Query("select v from Vehicle v where v.isActive=true and v.status=:status")
    /** Finds active vehicles matching the requested availability status. */
    // Returns vehicles that match the requested assignment readiness state.
    // Supports dispatch selection by narrowing the fleet to a status value.
    // Returns a list because several vehicles can share the same availability.
    List<Vehicle> available(@Param("status") Availability status);
}
