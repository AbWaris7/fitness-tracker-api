package com.khan.code.fitness_tracker_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FitnessTracker")
@Table(name = "fitness_tracker")
public class FitnessTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String username;

    @NotBlank(message = "Activity is required")
    private String activity;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int calories;

}
