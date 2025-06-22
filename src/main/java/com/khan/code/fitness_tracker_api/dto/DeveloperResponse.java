package com.khan.code.fitness_tracker_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeveloperResponse {
    private Long id;
    private String email;
    private List<ApplicationInfo> applications;

    @Data
    @AllArgsConstructor
    public static class ApplicationInfo {
        private Long id;
        private String name;
        private String description;
        private String apikey;
    }
}

