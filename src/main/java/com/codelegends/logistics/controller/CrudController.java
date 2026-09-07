package com.codelegends.logistics.controller;

import com.codelegends.logistics.service.CrudService;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<D> {
    private final CrudService<?, D> service;

    protected CrudController(CrudService<?, D> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<D> create(@Valid @RequestBody D dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping
    public List<D> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public D getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public D update(@PathVariable Long id, @Valid @RequestBody D dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
