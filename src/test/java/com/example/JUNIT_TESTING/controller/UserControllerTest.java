package com.example.JUNIT_TESTING.controller;

import com.example.JUNIT_TESTING.dto.UserCreationRequest;
import com.example.JUNIT_TESTING.dto.UserCreationResponse;
import com.example.JUNIT_TESTING.dto.UserResponse;
import com.example.JUNIT_TESTING.dto.UserUpdateRequest;
import com.example.JUNIT_TESTING.model.User;
import com.example.JUNIT_TESTING.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(UserControllerTest.MockServiceConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    void testCreateUser() throws Exception {
        UserCreationRequest request = new UserCreationRequest(
                "John", "Doe", "john@example.com", "secret", "Male");
        UserCreationResponse response = new UserCreationResponse(
                "John", "john@example.com", "Male"
        );
        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
    @Test
    void testDeleteUser() throws Exception {

        //arrange
        var userId = UUID.randomUUID();
       doNothing(). when(userService).deleteUser(userId);
        //act
        mockMvc.perform(delete("/v1/{userId}",userId))
                .andExpect(status().isNoContent());
        //assert

    }
    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        var userId = UUID.randomUUID();
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender("Male");

        UserResponse response = UserResponse.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .gender("Male")
                .build();

        when(userService.updateUser(userId, request)).thenReturn(response);

        mockMvc.perform(put("/v1/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void testGetUser() throws Exception {
        //arrange
        List<UserResponse> users =List.of(
                UserResponse.builder()
                        .id(UUID.randomUUID())
                        .email("john@gmail.com")
                        .firstName("John")
                        .lastName("Doe")
                        .gender("Male")
                        .build(),
                UserResponse.builder()
                        .id(UUID.randomUUID())
                        .email("elgar@gmail.com")
                        .firstName("elgar")
                        .lastName("Mokaya")
                        .gender("Female")
                        .build()
        );
        //act
        when(userService.getAllUsers()).thenReturn(users);
        //assert
        mockMvc.perform(get("/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].email").value("elgar@gmail.com"));

    }

}
