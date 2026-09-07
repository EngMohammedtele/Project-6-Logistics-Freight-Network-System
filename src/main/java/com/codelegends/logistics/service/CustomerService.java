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
public class CustomerService extends CrudService<Customer, CustomerDTO> {
    /** Injects persistence access and shared rule validation for Customer resources. */
    public CustomerService(CustomerRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Customer.class);
    }

    @Override
    /** Creates a new Customer entity instance for create requests. */
    protected Customer newEntity() {
        return new Customer();
    }

    @Override
    /** Converts the persisted Customer entity to its DTO representation. */
    protected CustomerDTO toDTO(Customer entity) {
        return CustomerDTO.convertToDTO(entity);
    }

    @Override
    /** Copies validated DTO values and resolved relationships onto the Customer entity. */
    protected void copy(CustomerDTO dto, Customer entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setType(dto.getType());
    }
}
