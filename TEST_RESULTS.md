# Verification results

Date: 2026-09-07
Runtime: Java 17, Spring Boot 3.5.5, Hibernate 6.6.26, H2 in MySQL compatibility mode.

`mvn test`: 4 tests, 0 failures, 0 errors, 0 skipped.

- deliveryWorkflowAndRejections: reserve inventory, assign resources, distinct duplicate-sequence rejection, complete stop/route, release resources, delivered invoice, unpaid query, invoice stats, private field omission.
- insufficientInventoryAndCapacity: reject shortage, roll back a partially processed multi-line shipment, reject overweight vehicle assignment.
- validationAndSoftDelete: validation 400, unknown ID 404, successful update, soft delete and hidden reads.
- allSixteenCrudSurfaces: create/list/read/update/delete/read-deleted for all 16 resources, using valid workflow state. Delivery items/stops are tested while editable, invoices after delivery.

The tests use real Spring controllers, services, JPA and a real H2 database, with a separate transaction per request. They do not mock repositories.

All 16 repositories and entity mappings initialize successfully. Java source formatted with google-java-format (AOSP style).

Limitations: MySQL Server and interactive Postman were not run here. MySQL configuration and the complete Postman request collection are included for local verification. Actual MySQL schema screenshots and GitHub publication must be completed in your account. Optional bonus analytics are not implemented.
