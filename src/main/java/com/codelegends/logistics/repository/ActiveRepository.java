package com.codelegends.logistics.repository;

import com.codelegends.logistics.entity.BaseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

// Prevents Spring Data from creating a repository bean for this shared base contract.
@NoRepositoryBean
// Defines common active-record queries for all soft-deletable entities.
// Restricts the generic entity type to BaseClass so active and audit fields are available.
// Extends JpaRepository to inherit save, delete, and identifier-based lookup operations.
// Uses Long as the shared primary-key type for logistics entities.
public interface ActiveRepository<E extends BaseClass> extends JpaRepository<E, Long> {
    /** Returns only entities that have not been soft deleted. */
    // Uses a Spring Data derived query based on the inherited isActive flag.
    // Returns a list because multiple active rows can match the filter.
    // Keeps soft-deleted rows out of standard list responses.
    // Applies the same active-row convention across every concrete repository.
    // Supports service-layer reads that should ignore inactive records.
    List<E> findAllByIsActiveTrue();

    /** Looks up a single entity while excluding soft-deleted rows. */
    // Combines identifier lookup with the active-row predicate.
    // Returns Optional because the id may be missing or inactive.
    Optional<E> findByIdAndIsActiveTrue(Long id);
}
