package com.codelegends.logistics.service;

import com.codelegends.logistics.entity.BaseClass;
import com.codelegends.logistics.exception.ResourceNotFoundException;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

@Component
public class EntityAccess {
    @PersistenceContext private EntityManager em;

    public <E extends BaseClass> E get(Class<E> type, Long id) {
        E entity = id == null ? null : em.find(type, id);
        if (entity == null || !entity.isActive()) {
            throw new ResourceNotFoundException(type.getSimpleName() + " not found: " + id);
        }
        return entity;
    }

    public <E extends BaseClass> E lock(Class<E> type, Long id) {
        E entity = get(type, id);
        em.lock(entity, LockModeType.PESSIMISTIC_WRITE);
        return entity;
    }
}
