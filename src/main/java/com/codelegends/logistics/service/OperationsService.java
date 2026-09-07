package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;

import jakarta.persistence.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Coordinates multi-entity logistics workflows that go beyond simple CRUD operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OperationsService {
    /** Resolves and locks active entities used by workflow operations. */
    private final EntityAccess access;
    /** Applies business constraints during workflow transitions. */
    private final Rules rules;
    /** Creates shipment line items while reusing item-level validation. */
    private final ShipmentItemService items;
    /** Creates route records through the standard route service. */
    private final RouteService routes;
    /** Creates delivery stops through the standard stop service. */
    private final DeliveryStopService stops;
    /** Creates tracking events through the standard tracking service. */
    private final TrackingEventService events;
    /** Creates invoices through the standard invoice service. */
    private final InvoiceService invoices;

    /** Entity manager used for direct persistence and aggregate workflow checks. */
    @PersistenceContext private EntityManager em;

    /** Creates a shipment, persists its items, and returns the created shipment DTO. */
    public ShipmentDTO createShipment(Operations.CreateShipment dto) {
        Shipment s = new Shipment();
        s.setWarehouse(access.lock(Warehouse.class, dto.warehouseId()));
        s.setCustomer(access.get(Customer.class, dto.customerId()));
        s.setCarrier(access.get(Carrier.class, dto.carrierId()));
        s.setShipmentDate(LocalDateTime.now());
        s.setStatus(ShipmentStatus.CREATED);
        s.setTotalWeight(BigDecimal.ZERO);
        em.persist(s);
        Set<Long> seen = new HashSet<>();
        for (Operations.Line line : dto.items()) {
            rules.require(seen.add(line.productId()), "Duplicate product in shipment");
            items.create(
                    ShipmentItemDTO.builder()
                            .shipmentId(s.getId())
                            .productId(line.productId())
                            .quantity(line.quantity())
                            .build());
        }
        em.flush();
        return ShipmentDTO.convertToDTO(s);
    }

    /** Reassigns a carrier only while the shipment is still unplanned. */
    public ShipmentDTO assign(Long id, Operations.AssignCarrier dto) {
        Shipment s = access.lock(Shipment.class, id);
        rules.require(
                s.getStatus() == ShipmentStatus.CREATED,
                "Only CREATED shipments can be reassigned");
        rules.require(
                em.createQuery(
                                        "select count(d) from DeliveryStop d where d.isActive=true"
                                            + " and d.shipment.id=:id",
                                        Long.class)
                                .setParameter("id", id)
                                .getSingleResult()
                        == 0,
                "Shipment already assigned to route");
        s.setCarrier(access.get(Carrier.class, dto.carrierId()));
        return ShipmentDTO.convertToDTO(s);
    }

    /** Delegates planned route creation to the route service. */
    public RouteDTO build(Operations.BuildRoute dto) {
        return routes.create(
                RouteDTO.builder()
                        .vehicleId(dto.vehicleId())
                        .driverId(dto.driverId())
                        .routeDate(dto.routeDate())
                        .origin(dto.origin())
                        .destination(dto.destination())
                        .status(RouteStatus.PLANNED)
                        .build());
    }

    /** Creates a pending stop on the selected route. */
    public DeliveryStopDTO addStop(Long routeId, Operations.Stop dto) {
        return stops.create(
                DeliveryStopDTO.builder()
                        .routeId(routeId)
                        .shipmentId(dto.shipmentId())
                        .sequence(dto.sequence())
                        .address(dto.address())
                        .eta(dto.eta())
                        .status(StopStatus.PENDING)
                        .build());
    }

    /** Locks the shipment before recording a tracking event for it. */
    public TrackingEventDTO track(Long shipmentId, Operations.Track dto) {
        access.lock(Shipment.class, shipmentId);
        return events.create(
                TrackingEventDTO.builder()
                        .shipmentId(shipmentId)
                        .eventTime(dto.eventTime())
                        .location(dto.location())
                        .status(dto.status())
                        .note(dto.note())
                        .build());
    }

    /** Completes a delivery stop and updates route resource availability. */
    public DeliveryStopDTO complete(Long id) {
        DeliveryStop d = access.lock(DeliveryStop.class, id);
        Route r = access.lock(Route.class, d.getRoute().getId());
        rules.require(d.getStatus() != StopStatus.COMPLETED, "Stop already complete");
        Shipment s = access.get(Shipment.class, d.getShipment().getId());
        d.setStatus(StopStatus.COMPLETED);
        track(
                s.getId(),
                new Operations.Track(
                        LocalDateTime.now(),
                        d.getAddress(),
                        ShipmentStatus.DELIVERED,
                        "Delivery stop completed"));
        em.flush();
        long pending =
                em.createQuery(
                                "select count(d) from DeliveryStop d where d.isActive=true and"
                                    + " d.route.id=:id and d.status=:status",
                                Long.class)
                        .setParameter("id", r.getId())
                        .setParameter("status", StopStatus.PENDING)
                        .getSingleResult();
        if (pending == 0) {
            r.setStatus(RouteStatus.COMPLETED);
            r.getVehicle().setStatus(Availability.AVAILABLE);
            r.getDriver().setStatus(Availability.AVAILABLE);
        } else {
            r.setStatus(RouteStatus.IN_PROGRESS);
        }
        return DeliveryStopDTO.convertToDTO(d);
    }

    /** Creates an unpaid invoice owned by the shipment customer. */
    public InvoiceDTO invoice(Long shipmentId, Operations.GenerateInvoice dto) {
        Shipment s = access.lock(Shipment.class, shipmentId);
        return invoices.create(
                InvoiceDTO.builder()
                        .shipmentId(shipmentId)
                        .customerId(s.getCustomer().getId())
                        .amount(dto.amount())
                        .status(InvoiceStatus.UNPAID)
                        .issuedDate(LocalDateTime.now())
                        .build());
    }
}
