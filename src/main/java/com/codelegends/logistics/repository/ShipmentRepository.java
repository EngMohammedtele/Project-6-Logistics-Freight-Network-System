package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentRepository extends ActiveRepository<Shipment> {

    @Query("select s from Shipment s where s.isActive=true and s.status=:status")
    List<Shipment> byStatus(@Param("status") ShipmentStatus status);

    @Query("select s from Shipment s where s.isActive=true and s.customer.id=:customerId")
    List<Shipment> history(@Param("customerId") Long customerId);
}
