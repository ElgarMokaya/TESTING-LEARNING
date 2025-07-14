package com.example.JUNIT_TESTING.model;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testUserToUserResponse() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("secret")
                .gender("male")
                .build();

        UserResponse response = UserMapper.INSTANCE.userToUserResponse(user);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getGender(), response.getGender());
    }

    @Test
    void testUserCreationRequestToUser() {
        UserCreationRequest request = UserCreationRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .password("password123")
                .gender("female")
                .build();

        User user = UserMapper.INSTANCE.userCreationRequestToUser(request);
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getGender(), user.getGender());
    }
} 