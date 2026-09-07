package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.BaseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ActiveRepository<E extends BaseClass> extends JpaRepository<E, Long> {
    List<E> findAllByIsActiveTrue();

    Optional<E> findByIdAndIsActiveTrue(Long id);
}
