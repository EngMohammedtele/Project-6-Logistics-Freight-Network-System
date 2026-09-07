package com.codelegends.logistics.service;

import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.exception.BusinessException;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Rules {
    @PersistenceContext private EntityManager em;

    public void require(boolean condition, String message) {
        if (!condition) throw new BusinessException(message);
    }

    public void validateUpdate(BaseClass e, Object dto) {
        if (e instanceof Shipment s) {
            var d = (com.codelegends.logistics.dto.ShipmentDTO) dto;
            require(
                    d.getWarehouseId().equals(s.getWarehouse().getId())
                            && d.getCustomerId().equals(s.getCustomer().getId()),
                    "Shipment ownership cannot change");
            require(
                    d.getCarrierId().equals(s.getCarrier().getId()),
                    "Use carrier assignment operation");
            require(
                    d.getStatus() == s.getStatus()
                            && d.getTotalWeight().compareTo(s.getTotalWeight()) == 0,
                    "Use tracking operations; shipment weight is derived");
        }
        if (e instanceof ShipmentItem i) {
            var d = (com.codelegends.logistics.dto.ShipmentItemDTO) dto;
            require(
                    d.getShipmentId().equals(i.getShipment().getId())
                            && d.getProductId().equals(i.getProduct().getId()),
                    "Item product/shipment cannot change");
        }
        if (e instanceof Route r) {
            var d = (com.codelegends.logistics.dto.RouteDTO) dto;
            require(
                    d.getVehicleId().equals(r.getVehicle().getId())
                            && d.getDriverId().equals(r.getDriver().getId()),
                    "Route resources cannot change");
            require(
                    d.getStatus() == r.getStatus(),
                    "Complete delivery stops to change route status");
        }
        if (e instanceof DeliveryStop stop) {
            var d = (com.codelegends.logistics.dto.DeliveryStopDTO) dto;
            require(
                    d.getRouteId().equals(stop.getRoute().getId())
                            && d.getShipmentId().equals(stop.getShipment().getId()),
                    "Stop route/shipment cannot change");
            require(d.getStatus() == stop.getStatus(), "Use stop completion operation");
        }
        if (e instanceof Vehicle v && v.getStatus() == Availability.ASSIGNED) {
            var d = (com.codelegends.logistics.dto.VehicleDTO) dto;
            require(
                    d.getStatus() == v.getStatus()
                            && d.getCapacityKg().compareTo(v.getCapacityKg()) == 0
                            && d.getCarrierId().equals(v.getCarrier().getId()),
                    "Assigned vehicle resources cannot change");
        }
        if (e instanceof Driver driver && driver.getStatus() == Availability.ASSIGNED) {
            var d = (com.codelegends.logistics.dto.DriverDTO) dto;
            require(
                    d.getStatus() == driver.getStatus()
                            && d.getCarrierId().equals(driver.getCarrier().getId()),
                    "Assigned driver cannot change availability/carrier");
        }
        if (e instanceof Product product) {
            var d = (com.codelegends.logistics.dto.ProductDTO) dto;
            long count =
                    em.createQuery(
                                    "select count(i) from ShipmentItem i where i.isActive=true and"
                                        + " i.product.id=:id",
                                    Long.class)
                            .setParameter("id", product.getId())
                            .getSingleResult();
            require(
                    count == 0 || d.getWeightKg().compareTo(product.getWeightKg()) == 0,
                    "Weight cannot change after shipment use");
        }
    }

    public void beforeChange(BaseClass e) {
        if (e instanceof ShipmentItem i) {
            require(
                    i.getShipment().getStatus() == ShipmentStatus.CREATED,
                    "Only draft shipment items can change");
            adjustStock(i, i.getQuantity());
        }
    }

    private void adjustStock(ShipmentItem item, int delta) {
        List<InventoryItem> stock =
                em.createQuery(
                                "select i from InventoryItem i where i.isActive=true and"
                                    + " i.warehouse.id=:w and i.product.id=:p",
                                InventoryItem.class)
                        .setParameter("w", item.getShipment().getWarehouse().getId())
                        .setParameter("p", item.getProduct().getId())
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .getResultList();
        require(!stock.isEmpty(), "Product is not stocked in warehouse");
        InventoryItem i = stock.get(0);
        require(i.getQuantity() + delta >= 0, "Insufficient inventory");
        i.setQuantity(i.getQuantity() + delta);
    }

    public void beforeSave(BaseClass e, boolean creating) {
        if (e instanceof InventoryItem i) {
            em.lock(i.getWarehouse(), LockModeType.PESSIMISTIC_WRITE);
            long others =
                    em.createQuery(
                                    "select coalesce(sum(i.quantity),0) from InventoryItem i where"
                                        + " i.isActive=true and i.warehouse.id=:w and (:id is null"
                                        + " or i.id<>:id)",
                                    Long.class)
                            .setParameter("w", i.getWarehouse().getId())
                            .setParameter("id", i.getId())
                            .getSingleResult();
            require(
                    others + i.getQuantity() <= i.getWarehouse().getCapacity(),
                    "Warehouse capacity exceeded");
        }
        if (e instanceof Shipment s) {
            if (creating) {
                require(
                        s.getStatus() == ShipmentStatus.CREATED,
                        "New shipments must have CREATED status");
                require(
                        s.getTotalWeight().signum() == 0,
                        "Initial shipment weight must be zero; items determine weight");
            }
        }
        if (e instanceof ShipmentItem i) {
            require(
                    i.getShipment().getStatus() == ShipmentStatus.CREATED,
                    "Shipment must be CREATED");
            require(
                    em.createQuery(
                                            "select count(d) from DeliveryStop d where"
                                                + " d.isActive=true and d.shipment.id=:id",
                                            Long.class)
                                    .setParameter("id", i.getShipment().getId())
                                    .getSingleResult()
                            == 0,
                    "Cannot change items after route assignment");
            adjustStock(i, -i.getQuantity());
        }
        if (e instanceof Route r && creating) {
            em.lock(r.getVehicle(), LockModeType.PESSIMISTIC_WRITE);
            em.lock(r.getDriver(), LockModeType.PESSIMISTIC_WRITE);
            require(r.getVehicle().getStatus() == Availability.AVAILABLE, "Vehicle unavailable");
            require(r.getDriver().getStatus() == Availability.AVAILABLE, "Driver unavailable");
            require(
                    r.getVehicle().getCarrier().getId().equals(r.getDriver().getCarrier().getId()),
                    "Vehicle and driver must share a carrier");
            require(r.getStatus() == RouteStatus.PLANNED, "New route must be PLANNED");
            require(
                    !r.getRouteDate().isBefore(java.time.LocalDate.now()),
                    "Route date cannot be in the past");
            r.getVehicle().setStatus(Availability.ASSIGNED);
            r.getDriver().setStatus(Availability.ASSIGNED);
        }
        if (e instanceof DeliveryStop d) validateStop(d);
        if (e instanceof Invoice i) {
            require(
                    i.getShipment().getStatus() == ShipmentStatus.DELIVERED,
                    "Shipment is not delivered");
            require(
                    i.getShipment().getCustomer().getId().equals(i.getCustomer().getId()),
                    "Invoice customer must own shipment");
        }
    }

    public void validateStop(DeliveryStop d) {
        em.lock(d.getRoute(), LockModeType.PESSIMISTIC_WRITE);
        require(d.getRoute().getStatus() != RouteStatus.COMPLETED, "Route is already complete");
        require(
                d.getShipment().getStatus() != ShipmentStatus.DELIVERED,
                "Shipment is already delivered");
        require(
                d.getShipment()
                        .getCarrier()
                        .getId()
                        .equals(d.getRoute().getVehicle().getCarrier().getId()),
                "Shipment carrier differs from route carrier");
        require(d.getShipment().getTotalWeight().signum() > 0, "Shipment must contain items");
        List<DeliveryStop> others =
                em.createQuery(
                                "select d from DeliveryStop d where d.isActive=true and (:id is"
                                    + " null or d.id<>:id)",
                                DeliveryStop.class)
                        .setParameter("id", d.getId())
                        .getResultList();
        BigDecimal weight = d.getShipment().getTotalWeight();
        for (DeliveryStop other : others) {
            require(
                    !other.getShipment().getId().equals(d.getShipment().getId()),
                    "Shipment already has an active delivery stop");
            if (other.getRoute().getId().equals(d.getRoute().getId())) {
                require(!other.getSequence().equals(d.getSequence()), "Duplicate route sequence");
                weight = weight.add(other.getShipment().getTotalWeight());
            }
        }
        require(
                weight.compareTo(d.getRoute().getVehicle().getCapacityKg()) <= 0,
                "Vehicle capacity exceeded");
        require(
                d.getEta().toLocalDate().equals(d.getRoute().getRouteDate()),
                "ETA must be on route date");
    }

    public void afterSave(BaseClass e) {
        if (e instanceof ShipmentItem i) {
            BigDecimal weight =
                    em.createQuery(
                                    "select coalesce(sum(i.quantity * i.product.weightKg),0) from"
                                        + " ShipmentItem i where i.isActive=true and"
                                        + " i.shipment.id=:id",
                                    BigDecimal.class)
                            .setParameter("id", i.getShipment().getId())
                            .getSingleResult();
            i.getShipment().setTotalWeight(weight);
        }
        if (e instanceof TrackingEvent t && t.isActive()) {
            Shipment s = t.getShipment();
            require(
                    t.getStatus().ordinal() >= s.getStatus().ordinal(),
                    "Tracking cannot move shipment backwards");
            s.setStatus(t.getStatus());
        }
    }

    public void beforeDelete(BaseClass e) {
        if (e instanceof ShipmentItem) beforeChange(e);
        if (e instanceof Route r) {
            require(
                    r.getStatus() == RouteStatus.COMPLETED
                            || em.createQuery(
                                                    "select count(d) from DeliveryStop d where"
                                                        + " d.route.id=:id and d.isActive=true",
                                                    Long.class)
                                            .setParameter("id", r.getId())
                                            .getSingleResult()
                                    == 0,
                    "Complete or remove route stops first");
            r.getVehicle().setStatus(Availability.AVAILABLE);
            r.getDriver().setStatus(Availability.AVAILABLE);
        }
    }
}
