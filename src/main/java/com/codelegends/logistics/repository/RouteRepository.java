package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RouteRepository extends ActiveRepository<Route> {

    @Query(
            "select r from Route r where r.isActive=true and r.driver.id=:driverId and"
                + " r.routeDate=:date")
    List<Route> forDriver(@Param("driverId") Long driverId, @Param("date") LocalDate date);
}
