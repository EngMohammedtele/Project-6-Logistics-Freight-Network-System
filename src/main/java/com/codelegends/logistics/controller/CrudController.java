package com.codelegends.logistics.controller;

import com.codelegends.logistics.service.CrudService;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides shared REST endpoints for active CRUD resources managed by a CrudService.
 */
public abstract class CrudController<D> {
    /** Delegates CRUD endpoint work to the matching service implementation. */
    private final CrudService<?, D> service;

    /** Supplies the service dependency used by the inherited REST operations. */
    protected CrudController(CrudService<?, D> service) {
        this.service = service;
    }

    @PostMapping
    /** Creates a validated resource and returns HTTP 201 with the created DTO. */
    public ResponseEntity<D> create(@Valid @RequestBody D dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping
    /** Returns all active resources visible through the service layer. */
    public List<D> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    /** Retrieves one active resource by path variable ID. */
    public D getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    /** Updates a validated resource body for the requested ID. */
    public D update(@PathVariable Long id, @Valid @RequestBody D dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    /** Soft deletes the requested resource and returns HTTP 204. */
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
