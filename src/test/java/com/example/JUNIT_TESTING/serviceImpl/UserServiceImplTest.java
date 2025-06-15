package com.example.JUNIT_TESTING.serviceImpl;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserCreationResponse;
import com.example.JUNIT_TESTING.model.User;
import com.example.JUNIT_TESTING.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser() {
        // Given
        UserCreationRequest request = UserCreationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("secret")
                .gender("Male")
                .build();

        User savedUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .gender(request.getGender())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserCreationResponse response = userService.createUser(request);

        // Then
        // Capture the User passed to repository.save to verify it's constructed correctly
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("John", capturedUser.getFirstName());
        assertEquals("Doe", capturedUser.getLastName());
        assertEquals("john@example.com", capturedUser.getEmail());
        assertEquals("secret", capturedUser.getPassword());
        assertEquals("Male", capturedUser.getGender());

        // Verify the response fields
        assertEquals("John", response.getFirstName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("Male", response.getGender());
    }
}