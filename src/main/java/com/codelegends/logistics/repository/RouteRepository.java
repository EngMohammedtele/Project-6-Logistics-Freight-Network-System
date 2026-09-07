package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

// Imports Query so the repository can declare explicit JPQL statements.
import org.springframework.data.jpa.repository.Query;
// Imports Param to bind method arguments into named JPQL parameters.
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides active-record persistence operations for route entities.
 */
// Binds Route persistence to the shared active-record repository contract.
// Uses Route as the aggregate root for inherited CRUD operations.
// Inherits Long-based identifier handling from ActiveRepository.
// Reuses active-row lookup methods for route database access.
// Delegates standard persistence behavior to Spring Data JPA.
public interface RouteRepository extends ActiveRepository<Route> {

    // Executes a custom JPQL query instead of relying only on method-name derivation.
    // Filters active routes assigned to a specific driver on a specific date.
    @Query(
            "select r from Route r where r.isActive=true and r.driver.id=:driverId and"
                + " r.routeDate=:date")
    /** Finds active routes assigned to a driver on a specific date. */
    // Returns driver route assignments that match the requested schedule day.
    // Uses the driver identifier to select only that driver's routes.
    // Uses the route date to limit assignments to one operating day.
    // Returns a list to support multiple route assignments on the same date.
    // Binds the driver id argument into the JPQL route assignment predicate.
    List<Route> forDriver(@Param("driverId") Long driverId, @Param("date") LocalDate date);
}
