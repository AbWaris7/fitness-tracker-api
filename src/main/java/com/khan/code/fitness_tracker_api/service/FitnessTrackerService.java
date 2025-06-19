package com.khan.code.fitness_tracker_api.service;


import com.khan.code.fitness_tracker_api.entity.FitnessTracker;

import java.util.List;

public interface FitnessTrackerService {

     List<FitnessTracker> findAll();
     FitnessTracker save(FitnessTracker fitnessTracker);
}
