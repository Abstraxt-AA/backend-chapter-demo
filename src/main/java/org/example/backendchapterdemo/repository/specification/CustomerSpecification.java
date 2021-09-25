package org.example.backendchapterdemo.repository.specification;

import org.apache.commons.lang3.ClassUtils;
import org.example.backendchapterdemo.dto.request.CustomerRequest;
import org.example.backendchapterdemo.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerSpecification implements Specification<Customer> {


    private final CustomerRequest customerRequest;

    public CustomerSpecification(CustomerRequest customerRequest) {
        this.customerRequest = customerRequest;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(customerRequest)) {
            if (Objects.nonNull(customerRequest.getUsername())) {
                predicates.add(username(customerRequest.getUsername())
                        .toPredicate(root, criteriaQuery, criteriaBuilder));
            }
            if (Objects.nonNull(customerRequest.getFirstName())) {
                predicates.add(firstName(customerRequest.getFirstName())
                        .toPredicate(root, criteriaQuery, criteriaBuilder));
            }
            if (Objects.nonNull(customerRequest.getLastName())) {
                predicates.add(lastName(customerRequest.getLastName())
                        .toPredicate(root, criteriaQuery, criteriaBuilder));
            }
        }

        if (Long.class != ClassUtils.primitiveToWrapper(criteriaQuery.getResultType()))
            root.fetch("user", JoinType.LEFT);
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }

    private Specification<Customer> username(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get("user").get("username"), "%" + query + "%"));
    }

    private Specification<Customer> firstName(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get("firstName"), "%" + query + "%"));
    }

    private Specification<Customer> lastName(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get("lastName"), "%" + query + "%"));
    }
}
