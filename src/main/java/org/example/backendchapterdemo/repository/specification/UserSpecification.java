package org.example.backendchapterdemo.repository.specification;

import org.apache.commons.lang3.ClassUtils;
import org.example.backendchapterdemo.dto.request.UserRequest;
import org.example.backendchapterdemo.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecification implements Specification<User> {

    private final UserRequest userRequest;

    public UserSpecification(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(userRequest)) {
            if (Objects.nonNull(userRequest.getUsername())) {
                predicates.add(username(userRequest.getUsername()).toPredicate(root, criteriaQuery, criteriaBuilder));
            }
            if (Objects.nonNull(userRequest.getRole())) {
                predicates.add(role(userRequest.getRole()).toPredicate(root, criteriaQuery, criteriaBuilder));
            }
        }

        if (Long.class != ClassUtils.primitiveToWrapper(criteriaQuery.getResultType()))
            root.fetch("customer", JoinType.LEFT);
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }

    private Specification<User> username(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%" + query + "%"));
    }

    private Specification<User> role(User.Roles query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.equal(root.get("role"), "%" + query + "%"));
    }
}
