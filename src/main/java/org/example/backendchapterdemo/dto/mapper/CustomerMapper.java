package org.example.backendchapterdemo.dto.mapper;

import org.example.backendchapterdemo.dto.request.CustomerRequest;
import org.example.backendchapterdemo.dto.response.CustomerResponse;
import org.example.backendchapterdemo.entity.Customer;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface CustomerMapper {

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password")
    @Mapping(target = "userId", ignore = true)
    Customer customerRequestToCustomer(CustomerRequest customerRequest);
    
    @Mapping(target = ".", source = "user")
    CustomerResponse customerToCustomerResponse(Customer customer);

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password")
    @Mapping(target = "userId", ignore = true)
    void updateCustomerByCustomerRequest(@MappingTarget Customer customer, CustomerRequest customerRequest);
}
