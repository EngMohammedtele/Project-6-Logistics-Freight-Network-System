package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

/**
 * Applies CRUD persistence and DTO mapping for customer resources.
 */
// Registers this class as the Spring service for customer workflows.
@Service
// Specializes the shared CRUD workflow for Customer entities and DTOs.
public class CustomerService extends CrudService<Customer, CustomerDTO> {
    /** Injects persistence access and shared rule validation for Customer resources. */
    public CustomerService(CustomerRepository repository, EntityAccess access, Rules rules) {
        // Passes the repository, entity access helper, and rules engine to the shared CRUD base.
        super(repository, access, rules, Customer.class);
    }

    @Override
    /** Creates a new Customer entity instance for create requests. */
    protected Customer newEntity() {
        // Returns a blank Customer instance that the copy method will populate.
        return new Customer();
    }

    @Override
    /** Converts the persisted Customer entity to its DTO representation. */
    protected CustomerDTO toDTO(Customer entity) {
        // Reuses the DTO mapper to expose persisted customer values to callers.
        return CustomerDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Customer entity. */
    // Maps request DTO fields onto the mutable Customer entity.
    protected void copy(CustomerDTO dto, Customer entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setType(dto.getType());
    }
}
