package com.codelegends.logistics.service;

import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.ActiveRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class CrudService<E extends BaseClass, D> {
    protected final ActiveRepository<E> repository;
    protected final EntityAccess access;
    protected final Rules rules;
    private final Class<E> type;

    protected CrudService(
            ActiveRepository<E> repository, EntityAccess access, Rules rules, Class<E> type) {
        this.repository = repository;
        this.access = access;
        this.rules = rules;
        this.type = type;
    }

    protected abstract E newEntity();

    protected abstract D toDTO(E entity);

    protected abstract void copy(D dto, E entity);

    public D create(D dto) {
        E entity = newEntity();
        copy(dto, entity);
        rules.beforeSave(entity, true);
        repository.saveAndFlush(entity);
        rules.afterSave(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<D> getAll() {
        return repository.findAllByIsActiveTrue().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public D getById(Long id) {
        return toDTO(access.get(type, id));
    }

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

    public void delete(Long id) {
        E entity = access.lock(type, id);
        rules.beforeDelete(entity);
        entity.setActive(false);
        repository.saveAndFlush(entity);
        rules.afterSave(entity);
    }
}
