package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.BaseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

// Prevents Spring Data from creating a repository bean for this shared base contract.
@NoRepositoryBean
// Defines common active-record queries for all soft-deletable entities.
public interface ActiveRepository<E extends BaseClass> extends JpaRepository<E, Long> {
    /** Returns only entities that have not been soft deleted. */
    List<E> findAllByIsActiveTrue();

    /** Looks up a single entity while excluding soft-deleted rows. */
    Optional<E> findByIdAndIsActiveTrue(Long id);
}
