package com.example.JUNIT_TESTING.model;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);

    User userCreationRequestToUser(UserCreationRequest request);
} 