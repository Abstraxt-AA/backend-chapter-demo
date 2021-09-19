package org.example.backendchapterdemo.repository;

import org.example.backendchapterdemo.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    List<Customer> findAll();
}
