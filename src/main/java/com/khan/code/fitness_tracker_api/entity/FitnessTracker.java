package com.khan.code.fitness_tracker_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FitnessTrackers")
@Table(name = "fitness_tracker")
public class FitnessTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String activity;
    private int duration;
    private int calories;

}
