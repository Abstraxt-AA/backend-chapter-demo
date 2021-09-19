package org.example.backendchapterdemo.dto.mapper;

import org.example.backendchapterdemo.dto.request.UserRequest;
import org.example.backendchapterdemo.dto.response.UserResponse;
import org.example.backendchapterdemo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    User userRequestToUser(UserRequest userRequest);

    UserResponse userToUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    void updateUserByUserRequest(@MappingTarget User user, UserRequest userRequest);
}
