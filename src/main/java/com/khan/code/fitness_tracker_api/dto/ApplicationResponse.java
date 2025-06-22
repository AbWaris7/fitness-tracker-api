package com.khan.code.fitness_tracker_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApplicationResponse {
    private String name;
    private String apikey;
}

