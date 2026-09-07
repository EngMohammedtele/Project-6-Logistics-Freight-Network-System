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

@Service
@RequiredArgsConstructor
@Transactional
public class OperationsService {
    private final EntityAccess access;
    private final Rules rules;
    private final ShipmentItemService items;
    private final RouteService routes;
    private final DeliveryStopService stops;
    private final TrackingEventService events;
    private final InvoiceService invoices;

    @PersistenceContext private EntityManager em;

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
