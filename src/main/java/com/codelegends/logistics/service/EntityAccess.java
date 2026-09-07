package com.codelegends.logistics.service;

import com.codelegends.logistics.entity.BaseClass;
import com.codelegends.logistics.exception.ResourceNotFoundException;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

/**
 * Centralizes active-entity lookup and pessimistic locking for service workflows.
 */
// Registers the lookup helper so services can share active-entity resolution.
@Component
public class EntityAccess {
    /** Entity manager used for active lookups and pessimistic locks. */
    @PersistenceContext private EntityManager em;

    /** Returns an active entity or raises a not-found exception. */
    public <E extends BaseClass> E get(Class<E> type, Long id) {
        E entity = id == null ? null : em.find(type, id);
        if (entity == null || !entity.isActive()) {
            throw new ResourceNotFoundException(type.getSimpleName() + " not found: " + id);
        }
        return entity;
    }

    /** Resolves an entity and applies a pessimistic write lock. */
    public <E extends BaseClass> E lock(Class<E> type, Long id) {
        E entity = get(type, id);
        em.lock(entity, LockModeType.PESSIMISTIC_WRITE);
        return entity;
    }
}
