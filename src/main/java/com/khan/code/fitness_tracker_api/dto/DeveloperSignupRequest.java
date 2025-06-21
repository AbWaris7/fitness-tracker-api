package com.khan.code.fitness_tracker_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeveloperSignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
