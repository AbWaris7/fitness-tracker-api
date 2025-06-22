package com.khan.code.fitness_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRegisterRequest {
    @NotBlank
    private String name;

    @NotNull
    private String description;
}
