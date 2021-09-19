package org.example.backendchapterdemo.service.impl;

import org.example.backendchapterdemo.dto.mapper.CustomerMapper;
import org.example.backendchapterdemo.dto.request.CustomerRequest;
import org.example.backendchapterdemo.dto.response.CustomerResponse;
import org.example.backendchapterdemo.entity.Customer;
import org.example.backendchapterdemo.repository.CustomerRepository;
import org.example.backendchapterdemo.repository.specification.CustomerSpecification;
import org.example.backendchapterdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CustomerResponse> getCustomerPage(CustomerRequest customerRequest, Pageable pageable) {
        return customerRepository.findAll(new CustomerSpecification(customerRequest), pageable)
                .map(customerMapper::customerToCustomerResponse);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        return customerMapper
                .customerToCustomerResponse(customerRepository
                        .save(customerMapper.customerRequestToCustomer(customerRequest)));
    }

    @Override
    public Optional<CustomerResponse> deleteCustomer(UUID userId) {
        Optional<Customer> customer = customerRepository.findById(userId);
        customer.ifPresent(customerRepository::delete);
        return customer.map(customerMapper::customerToCustomerResponse);
    }

    @Override
    public Optional<CustomerResponse> updateCustomer(UUID userId, CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findById(userId);
        customer.map(oldCustomer -> {
            customerMapper.updateCustomerByCustomerRequest(oldCustomer, customerRequest);
            return oldCustomer;
        });
        customer.ifPresent(customerRepository::save);
        return customer.map(customerMapper::customerToCustomerResponse);
    }

    @Override
    public List<CustomerResponse> saveBulk(List<CustomerRequest> customers) {
        return customerRepository
                .saveAll(customers.stream().map(customerMapper::customerRequestToCustomer).collect(Collectors.toList()))
                .stream().map(customerMapper::customerToCustomerResponse).collect(Collectors.toList());
    }
}
