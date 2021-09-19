package org.example.backendchapterdemo.service.impl;

import org.example.backendchapterdemo.dto.mapper.UserMapper;
import org.example.backendchapterdemo.dto.request.UserRequest;
import org.example.backendchapterdemo.dto.response.UserResponse;
import org.example.backendchapterdemo.entity.User;
import org.example.backendchapterdemo.repository.UserRepository;
import org.example.backendchapterdemo.repository.specification.UserSpecification;
import org.example.backendchapterdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> getUserPage(UserRequest userRequest, Pageable pageable) {
        return userRepository.findAll(new UserSpecification(userRequest), pageable).map(userMapper::userToUserResponse);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return userMapper
                .userToUserResponse(userRepository
                        .save(userMapper.userRequestToUser(userRequest)));
    }

    @Override
    public Optional<UserResponse> deleteUser(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(userRepository::delete);
        return user.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> updateUser(UUID userId, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(userId);
        user.map(oldUser -> {
            userMapper.updateUserByUserRequest(oldUser, userRequest);
            return oldUser;
        });
        user.ifPresent(userRepository::save);
        return user.map(userMapper::userToUserResponse);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).collect(Collectors.toList());
    }
    
    @Override
    public List<UserResponse> saveBulk(List<UserRequest> users) {
        return userRepository
                .saveAll(users.stream().map(userMapper::userRequestToUser).collect(Collectors.toList()))
                .stream().map(userMapper::userToUserResponse).collect(Collectors.toList());
    }

}
