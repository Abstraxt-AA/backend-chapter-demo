package org.example.backendchapterdemo.service;

import org.example.backendchapterdemo.dto.request.UserRequest;
import org.example.backendchapterdemo.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    
    Page<UserResponse> getUserPage(UserRequest userRequest, Pageable pageable);

    UserResponse createUser(UserRequest userRequest);

    Optional<UserResponse> deleteUser(UUID userId);

    Optional<UserResponse> updateUser(UUID userId, UserRequest userRequest);

    List<UserResponse> getAllUsers();

    List<UserResponse> saveBulk(List<UserRequest> users);
}
