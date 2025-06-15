package com.example.JUNIT_TESTING.service;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserCreationResponse;
import com.example.JUNIT_TESTING.dto.UserResponse;
import com.example.JUNIT_TESTING.dto.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface UserService {
    UserCreationResponse createUser(UserCreationRequest userCreationRequest);
    UserResponse getUserById(UUID id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(UUID id, UserUpdateRequest request);
    void deleteUser(UUID id);
}
