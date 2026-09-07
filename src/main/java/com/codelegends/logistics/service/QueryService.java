package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import jakarta.persistence.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides read-only reporting and lookup operations backed by repository queries and aggregate JPQL.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryService {
    private final ShipmentRepository shipments;
    private final InventoryItemRepository inventory;
    private final RouteRepository routes;
    private final VehicleRepository vehicles;
    private final InvoiceRepository invoices;
    private final EntityAccess access;
    @PersistenceContext private EntityManager em;

    /** Maps active shipments with the requested status into DTOs. */
    public List<ShipmentDTO> status(ShipmentStatus status) {
        return ShipmentDTO.convertToDTO(shipments.byStatus(status));
    }

    /** Validates the threshold and returns low-stock inventory records. */
    public List<InventoryItemDTO> below(Integer threshold) {
        if (threshold < 0) throw new IllegalArgumentException("Threshold must be nonnegative");
        return InventoryItemDTO.convertToDTO(inventory.below(threshold));
    }

    /** Verifies the driver exists before returning matching routes. */
    public List<RouteDTO> driver(Long id, LocalDate date) {
        access.get(Driver.class, id);
        return RouteDTO.convertToDTO(routes.forDriver(id, date));
    }

    /** Returns vehicles currently marked available. */
    public List<VehicleDTO> available() {
        return VehicleDTO.convertToDTO(vehicles.available(Availability.AVAILABLE));
    }

    /** Verifies the customer exists before returning shipment history. */
    public List<ShipmentDTO> history(Long id) {
        access.get(Customer.class, id);
        return ShipmentDTO.convertToDTO(shipments.history(id));
    }

    /** Verifies the customer exists before returning unpaid invoices. */
    public List<InvoiceDTO> unpaid(Long id) {
        access.get(Customer.class, id);
        return InvoiceDTO.convertToDTO(invoices.unpaid(id, InvoiceStatus.UNPAID));
    }

    /** Aggregates shipment and inventory statistics for a warehouse. */
    public StatsDTO warehouse(Long id) {
        access.get(Warehouse.class, id);
        long shipments =
                count(
                        "select count(s) from Shipment s where s.isActive=true and"
                            + " s.warehouse.id=:id",
                        id);
        long units =
                count(
                        "select coalesce(sum(i.quantity),0) from InventoryItem i where"
                            + " i.isActive=true and i.warehouse.id=:id",
                        id);
        return StatsDTO.builder().id(id).activeShipments(shipments).inventoryUnits(units).build();
    }

    /** Aggregates shipment, vehicle, driver, route, and invoice statistics for a carrier. */
    public StatsDTO carrier(Long id) {
        access.get(Carrier.class, id);
        return StatsDTO.builder()
                .id(id)
                .vehicles(
                        count(
                                "select count(v) from Vehicle v where v.isActive=true and"
                                    + " v.carrier.id=:id",
                                id))
                .drivers(
                        count(
                                "select count(d) from Driver d where d.isActive=true and"
                                    + " d.carrier.id=:id",
                                id))
                .activeRoutes(
                        count(
                                "select count(r) from Route r where r.isActive=true and"
                                    + " r.vehicle.carrier.id=:id",
                                id))
                .build();
    }

    /** Aggregates shipment and invoice totals for a customer. */
    public StatsDTO customer(Long id) {
        access.get(Customer.class, id);
        BigDecimal total =
                em.createQuery(
                                "select coalesce(sum(i.amount),0) from Invoice i where"
                                    + " i.isActive=true and i.customer.id=:id",
                                BigDecimal.class)
                        .setParameter("id", id)
                        .getSingleResult();
        return StatsDTO.builder().id(id).totalInvoiced(total).build();
    }

    /** Executes a count query using a shared ID parameter. */
    private long count(String query, Long id) {
        return em.createQuery(query, Long.class).setParameter("id", id).getSingleResult();
    }
}
