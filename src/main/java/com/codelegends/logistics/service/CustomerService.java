package com.codelegends.logistics.service;

import com.codelegends.logistics.dto.*;
import com.codelegends.logistics.entity.*;
import com.codelegends.logistics.repository.*;

import org.springframework.stereotype.Service;

@Service
public class CustomerService extends CrudService<Customer, CustomerDTO> {
    public CustomerService(CustomerRepository repository, EntityAccess access, Rules rules) {
        super(repository, access, rules, Customer.class);
    }

    @Override
    protected Customer newEntity() {
        return new Customer();
    }

    @Override
    protected CustomerDTO toDTO(Customer entity) {
        return CustomerDTO.convertToDTO(entity);
    }

    @Override
    protected void copy(CustomerDTO dto, Customer entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setType(dto.getType());
    }
}
