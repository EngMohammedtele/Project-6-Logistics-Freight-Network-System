package com.codelegends.logistics;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.*;

/**
 * Exercises the main logistics workflows, CRUD endpoints, validation, and soft-delete behavior through MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@org.springframework.test.annotation.DirtiesContext(
        classMode =
                org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LogisticsIntegrationTest {
    /** MockMvc client used to exercise HTTP endpoints without starting a server. */
    @Autowired MockMvc mvc;
    /** ObjectMapper used to serialize request bodies and parse JSON responses. */
    @Autowired ObjectMapper mapper;

    /** Performs an HTTP request and asserts the expected status code. */
    JsonNode request(String method, String path, Object body, int expected) throws Exception {
        var builder =
                switch (method) {
                    case "POST" -> post(path);
                    case "PUT" -> put(path);
                    case "DELETE" -> delete(path);
                    default -> get(path);
                };
        builder.contentType("application/json");
        if (body != null) builder.content(mapper.writeValueAsString(body));
        var response = mvc.perform(builder).andReturn().getResponse();
        assertEquals(expected, response.getStatus(), path + " " + response.getContentAsString());
        return response.getContentAsString().isBlank()
                ? mapper.createObjectNode()
                : mapper.readTree(response.getContentAsString());
    }

    /** Creates a resource through its REST endpoint and returns the generated ID. */
    long create(String resource, Object body) throws Exception {
        return request("POST", "/api/" + resource, body, 201).get("id").asLong();
    }

    Map<String, Long> setup() throws Exception {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        long w =
                create("warehouses", Map.of("name", "Main", "location", "Muscat", "capacity", 100));
        long p =
                create(
                        "products",
                        Map.of("name", "Box", "sku", suffix, "weightKg", 5, "category", "General"));
        long i =
                create(
                        "inventory-items",
                        Map.of(
                                "quantity",
                                10,
                                "shelfLocation",
                                "A1",
                                "warehouseId",
                                w,
                                "productId",
                                p));
        long c =
                create(
                        "customers",
                        Map.of(
                                "name",
                                "Mohammed",
                                "email",
                                "m@example.com",
                                "phoneNumber",
                                "90000000",
                                "type",
                                "INDIVIDUAL"));
        long k =
                create(
                        "carriers",
                        Map.of(
                                "name",
                                "Carrier",
                                "contactEmail",
                                "c@example.com",
                                "phoneNumber",
                                "90000000",
                                "country",
                                "Oman"));
        long v =
                create(
                        "vehicles",
                        Map.of(
                                "plateNumber",
                                suffix,
                                "type",
                                "VAN",
                                "capacityKg",
                                20,
                                "status",
                                "AVAILABLE",
                                "carrierId",
                                k));
        long d =
                create(
                        "drivers",
                        Map.of(
                                "name",
                                "Driver",
                                "licenseNumber",
                                suffix,
                                "phoneNumber",
                                "90000000",
                                "status",
                                "AVAILABLE",
                                "carrierId",
                                k));
        return Map.of("w", w, "p", p, "i", i, "c", c, "k", k, "v", v, "d", d);
    }

    /** Builds an operations shipment payload for the shared fixture data. */
    Map<String, Object> shipment(Map<String, Long> x, int q) {
        return Map.of(
                "warehouseId",
                x.get("w"),
                "customerId",
                x.get("c"),
                "carrierId",
                x.get("k"),
                "items",
                List.of(Map.of("productId", x.get("p"), "quantity", q)));
    }

    @Test
    /** Verifies the delivery workflow and expected business-rule rejections. */
    void deliveryWorkflowAndRejections() throws Exception {
        var x = setup();
        long s =
                request("POST", "/api/operations/shipments", shipment(x, 3), 201)
                        .get("id")
                        .asLong();
        assertEquals(
                7,
                request("GET", "/api/inventory-items/" + x.get("i"), null, 200)
                        .get("quantity")
                        .asInt());
        request("POST", "/api/operations/shipments/" + s + "/invoice", Map.of("amount", 25), 400);
        var routeBody =
                Map.of(
                        "vehicleId",
                        x.get("v"),
                        "driverId",
                        x.get("d"),
                        "routeDate",
                        LocalDate.now().plusDays(1).toString(),
                        "origin",
                        "Muscat",
                        "destination",
                        "Ibri");
        long r = request("POST", "/api/operations/routes", routeBody, 201).get("id").asLong();
        request("POST", "/api/operations/routes", routeBody, 400);
        var stopBody =
                Map.of(
                        "shipmentId",
                        s,
                        "sequence",
                        1,
                        "address",
                        "Ibri",
                        "eta",
                        LocalDate.now().plusDays(1).atTime(12, 0).toString());
        long stop =
                request("POST", "/api/operations/routes/" + r + "/stops", stopBody, 201)
                        .get("id")
                        .asLong();
        request("POST", "/api/operations/routes/" + r + "/stops", stopBody, 400);
        long second =
                request("POST", "/api/operations/shipments", shipment(x, 1), 201)
                        .get("id")
                        .asLong();
        var duplicate =
                request(
                        "POST",
                        "/api/operations/routes/" + r + "/stops",
                        Map.of(
                                "shipmentId",
                                second,
                                "sequence",
                                1,
                                "address",
                                "Ibri",
                                "eta",
                                LocalDate.now().plusDays(1).atTime(13, 0).toString()),
                        400);
        assertTrue(duplicate.get("message").asText().contains("sequence"));

        request(
                "PUT",
                "/api/operations/stops/" + stop + "/complete",
                Map.of("completed", true),
                200);
        assertEquals(
                "COMPLETED", request("GET", "/api/routes/" + r, null, 200).get("status").asText());
        assertEquals(
                "AVAILABLE",
                request("GET", "/api/vehicles/" + x.get("v"), null, 200).get("status").asText());
        request("POST", "/api/operations/shipments/" + s + "/invoice", Map.of("amount", 25), 201);
        assertEquals(
                1,
                request(
                                "GET",
                                "/api/queries/customers/" + x.get("c") + "/unpaid-invoices",
                                null,
                                200)
                        .size());
        assertEquals(
                25,
                request("GET", "/api/queries/customers/" + x.get("c") + "/stats", null, 200)
                        .get("totalInvoiced")
                        .asInt());
        assertFalse(request("GET", "/api/customers/" + x.get("c"), null, 200).has("phoneNumber"));
    }

    @Test
    /** Verifies stock rollback and capacity validation scenarios. */
    void insufficientInventoryAndCapacity() throws Exception {
        var x = setup();
        request("POST", "/api/operations/shipments", shipment(x, 11), 400);
        assertEquals(
                10,
                request("GET", "/api/inventory-items/" + x.get("i"), null, 200)
                        .get("quantity")
                        .asInt());
        request(
                "POST",
                "/api/operations/shipments",
                Map.of(
                        "warehouseId",
                        x.get("w"),
                        "customerId",
                        x.get("c"),
                        "carrierId",
                        x.get("k"),
                        "items",
                        List.of(
                                Map.of("productId", x.get("p"), "quantity", 2),
                                Map.of("productId", 999999, "quantity", 1))),
                404);
        assertEquals(
                10,
                request("GET", "/api/inventory-items/" + x.get("i"), null, 200)
                        .get("quantity")
                        .asInt());

        long s =
                request("POST", "/api/operations/shipments", shipment(x, 5), 201)
                        .get("id")
                        .asLong();
        long r =
                request(
                                "POST",
                                "/api/operations/routes",
                                Map.of(
                                        "vehicleId",
                                        x.get("v"),
                                        "driverId",
                                        x.get("d"),
                                        "routeDate",
                                        LocalDate.now().plusDays(1).toString(),
                                        "origin",
                                        "A",
                                        "destination",
                                        "B"),
                                201)
                        .get("id")
                        .asLong();
        request(
                "POST",
                "/api/operations/routes/" + r + "/stops",
                Map.of(
                        "shipmentId",
                        s,
                        "sequence",
                        1,
                        "address",
                        "B",
                        "eta",
                        LocalDate.now().plusDays(1).atTime(12, 0).toString()),
                400);
    }

    @Test
    /** Verifies request validation, not-found handling, updates, and soft deletion. */
    void validationAndSoftDelete() throws Exception {
        request(
                "POST",
                "/api/warehouses",
                Map.of("name", "", "location", "Muscat", "capacity", -1),
                400);
        request("GET", "/api/warehouses/999999", null, 404);
        long id =
                create(
                        "warehouses",
                        Map.of("name", "Temporary", "location", "Muscat", "capacity", 10));
        request(
                "PUT",
                "/api/warehouses/" + id,
                Map.of("name", "Updated", "location", "Ibri", "capacity", 20),
                200);
        request("DELETE", "/api/warehouses/" + id, null, 204);
        request("GET", "/api/warehouses/" + id, null, 404);
    }

    /** Exercises create, list, read, and update operations for a CRUD resource. */
    long exercise(String resource, Map<String, Object> body) throws Exception {
        long id = create(resource, body);
        request("GET", "/api/" + resource, null, 200);
        request("GET", "/api/" + resource + "/" + id, null, 200);
        request("PUT", "/api/" + resource + "/" + id, body, 200);
        return id;
    }

    /** Soft deletes a resource and confirms it is no longer readable. */
    void remove(String resource, long id) throws Exception {
        request("DELETE", "/api/" + resource + "/" + id, null, 204);
        request("GET", "/api/" + resource + "/" + id, null, 404);
    }

    @Test
    /** Verifies every exposed CRUD surface participates in the shared lifecycle. */
    void allSixteenCrudSurfaces() throws Exception {
        long w =
                exercise(
                        "warehouses",
                        Map.of("name", "Depot", "location", "Muscat", "capacity", 100));
        long p =
                exercise(
                        "products",
                        Map.of(
                                "name",
                                "Box",
                                "sku",
                                "CRUD-BOX",
                                "weightKg",
                                2,
                                "category",
                                "General"));
        long c =
                exercise(
                        "customers",
                        Map.of(
                                "name",
                                "Customer",
                                "email",
                                "c@example.com",
                                "phoneNumber",
                                "90000000",
                                "type",
                                "BUSINESS"));
        long k =
                exercise(
                        "carriers",
                        Map.of(
                                "name",
                                "Carrier",
                                "contactEmail",
                                "k@example.com",
                                "phoneNumber",
                                "90000000",
                                "country",
                                "Oman"));
        long z =
                exercise(
                        "service-zones", Map.of("name", "Zone", "region", "Muscat", "baseRate", 2));
        long a =
                exercise(
                        "addresses",
                        Map.of(
                                "street",
                                "Street",
                                "city",
                                "Muscat",
                                "postalCode",
                                "133",
                                "country",
                                "Oman",
                                "customerId",
                                c,
                                "serviceZoneId",
                                z));
        long staff =
                exercise(
                        "staff",
                        Map.of(
                                "name",
                                "Staff",
                                "role",
                                "Operator",
                                "phoneNumber",
                                "90000000",
                                "warehouseId",
                                w));
        long inventory =
                exercise(
                        "inventory-items",
                        Map.of(
                                "quantity",
                                20,
                                "shelfLocation",
                                "A1",
                                "warehouseId",
                                w,
                                "productId",
                                p));
        long vehicle =
                exercise(
                        "vehicles",
                        Map.of(
                                "plateNumber",
                                "CRUD-V",
                                "type",
                                "VAN",
                                "capacityKg",
                                100,
                                "status",
                                "AVAILABLE",
                                "carrierId",
                                k));
        long driver =
                exercise(
                        "drivers",
                        Map.of(
                                "name",
                                "Driver",
                                "licenseNumber",
                                "CRUD-L",
                                "phoneNumber",
                                "90000000",
                                "status",
                                "AVAILABLE",
                                "carrierId",
                                k));
        long shipment =
                exercise(
                        "shipments",
                        Map.of(
                                "shipmentDate",
                                LocalDateTime.now().minusMinutes(1).toString(),
                                "status",
                                "CREATED",
                                "totalWeight",
                                0,
                                "warehouseId",
                                w,
                                "customerId",
                                c,
                                "carrierId",
                                k));
        long item =
                exercise(
                        "shipment-items",
                        Map.of("quantity", 2, "shipmentId", shipment, "productId", p));
        remove("shipment-items", item);
        item =
                create(
                        "shipment-items",
                        Map.of("quantity", 2, "shipmentId", shipment, "productId", p));
        long route =
                exercise(
                        "routes",
                        Map.of(
                                "routeDate",
                                LocalDate.now().plusDays(1).toString(),
                                "origin",
                                "Muscat",
                                "destination",
                                "Ibri",
                                "status",
                                "PLANNED",
                                "vehicleId",
                                vehicle,
                                "driverId",
                                driver));
        long stop =
                exercise(
                        "delivery-stops",
                        Map.of(
                                "sequence",
                                1,
                                "address",
                                "Ibri",
                                "status",
                                "PENDING",
                                "eta",
                                LocalDate.now().plusDays(1).atTime(12, 0).toString(),
                                "routeId",
                                route,
                                "shipmentId",
                                shipment));
        remove("delivery-stops", stop);
        stop =
                create(
                        "delivery-stops",
                        Map.of(
                                "sequence",
                                2,
                                "address",
                                "Ibri",
                                "status",
                                "PENDING",
                                "eta",
                                LocalDate.now().plusDays(1).atTime(12, 0).toString(),
                                "routeId",
                                route,
                                "shipmentId",
                                shipment));
        long event =
                exercise(
                        "tracking-events",
                        Map.of(
                                "eventTime",
                                LocalDateTime.now().minusMinutes(1).toString(),
                                "location",
                                "Muscat",
                                "status",
                                "PICKED_UP",
                                "note",
                                "Collected",
                                "shipmentId",
                                shipment));
        request(
                "PUT",
                "/api/operations/stops/" + stop + "/complete",
                Map.of("completed", true),
                200);
        long invoice =
                exercise(
                        "invoices",
                        Map.of(
                                "amount",
                                20,
                                "status",
                                "UNPAID",
                                "issuedDate",
                                LocalDateTime.now().minusSeconds(1).toString(),
                                "shipmentId",
                                shipment,
                                "customerId",
                                c));
        remove("invoices", invoice);
        remove("tracking-events", event);
        remove("routes", route);
        remove("shipments", shipment);
        remove("addresses", a);
        remove("service-zones", z);
        remove("staff", staff);
        remove("inventory-items", inventory);
        remove("vehicles", vehicle);
        remove("drivers", driver);
        remove("carriers", k);
        remove("customers", c);
        remove("products", p);
        remove("warehouses", w);
    }
}
