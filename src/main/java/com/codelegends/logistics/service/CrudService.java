package com.codelegends.logistics.service;

import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.ActiveRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implements reusable transactional CRUD behavior for soft-deletable logistics entities.
 */
// Keeps write-oriented CRUD operations inside transactional boundaries.
@Transactional
// Provides the reusable service template for entity-to-DTO CRUD flows.
public abstract class CrudService<E extends BaseClass, D> {
    /** Repository used for active-record persistence operations. */
    protected final ActiveRepository<E> repository;
    /** Shared helper for resolving and locking active entities. */
    protected final EntityAccess access;
    /** Business-rule component invoked around persistence changes. */
    protected final Rules rules;
    /** Entity type used for generic lookups and lock operations. */
    private final Class<E> type;

    /** Wires the shared dependencies needed by concrete CRUD services. */
    // Receives concrete dependencies from each resource-specific service.
    protected CrudService(
            ActiveRepository<E> repository, EntityAccess access, Rules rules, Class<E> type) {
        this.repository = repository;
        this.access = access;
        this.rules = rules;
        this.type = type;
    }

    /** Creates a blank entity for a create request. */
    protected abstract E newEntity();

    /** Converts a persisted entity into its API DTO. */
    protected abstract D toDTO(E entity);

    /** Applies DTO values onto the supplied entity. */
    protected abstract void copy(D dto, E entity);

    /** Creates, validates, persists, and returns a new active resource. */
    public D create(D dto) {
        // Starts creation with a blank entity supplied by the concrete service.
        E entity = newEntity();
        // Applies incoming DTO values before save-time validation runs.
        copy(dto, entity);
        rules.beforeSave(entity, true);
        repository.saveAndFlush(entity);
        rules.afterSave(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    /** Reads all non-deleted resources and maps them to DTOs. */
    public List<D> getAll() {
        return repository.findAllByIsActiveTrue().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    /** Reads one active resource or raises a not-found exception. */
    public D getById(Long id) {
        return toDTO(access.get(type, id));
    }

    /** Locks, validates, updates, and returns an existing active resource. */
    public D update(Long id, D dto) {
        E entity = access.lock(type, id);
        rules.validateUpdate(entity, dto);
        rules.beforeChange(entity);
        copy(dto, entity);
        rules.beforeSave(entity, false);
        repository.saveAndFlush(entity);
        rules.afterSave(entity);
        return toDTO(entity);
    }

    /** Performs a soft delete after validating deletion rules. */
    public void delete(Long id) {
        E entity = access.lock(type, id);
        rules.beforeDelete(entity);
        entity.setActive(false);
        repository.saveAndFlush(entity);
        rules.afterSave(entity);
    }
}
