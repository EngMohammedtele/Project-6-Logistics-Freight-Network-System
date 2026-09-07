package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Provides active-record persistence operations for vehicle entities.
 */
// Binds Vehicle persistence to the shared active-record repository contract.
public interface VehicleRepository extends ActiveRepository<Vehicle> {

    @Query("select v from Vehicle v where v.isActive=true and v.status=:status")
    /** Finds active vehicles matching the requested availability status. */
    List<Vehicle> available(@Param("status") Availability status);
}
