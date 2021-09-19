package org.example.backendchapterdemo.service;

import org.example.backendchapterdemo.dto.request.CustomerRequest;
import org.example.backendchapterdemo.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponse> getAllCustomers();

    Page<CustomerResponse> getCustomerPage(CustomerRequest customerRequest, Pageable pageable);

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    Optional<CustomerResponse> deleteCustomer(UUID userId);

    Optional<CustomerResponse> updateCustomer(UUID userId, CustomerRequest customerRequest);

    List<CustomerResponse> saveBulk(List<CustomerRequest> customers);
}
