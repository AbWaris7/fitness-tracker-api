package com.khan.code.fitness_tracker_api.dao;

import com.khan.code.fitness_tracker_api.entity.FitnessTracker;

import java.util.List;

public interface FitnessTrackerDAO {

    public List<FitnessTracker> findAll();
}
