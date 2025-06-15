package com.example.JUNIT_TESTING.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserCreationResponse {
    private String firstName;
    private String email;
    private String gender;
}
