package com.example.JUNIT_TESTING.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
}
