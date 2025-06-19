package com.khan.code.fitness_tracker_api.service;

import com.khan.code.fitness_tracker_api.dao.FitnessTrackerDAO;
import com.khan.code.fitness_tracker_api.entity.FitnessTracker;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FitnessTrackerServiceImpl implements FitnessTrackerService {

    FitnessTrackerDAO fitnessTrackerDAO;


    public FitnessTrackerServiceImpl(FitnessTrackerDAO fitnessTrackerDAO) {
        this.fitnessTrackerDAO = fitnessTrackerDAO;
    }

    @Override
    public List<FitnessTracker> findAll() {
        return fitnessTrackerDAO.findAll();
    }

    @Override
    public FitnessTracker save(FitnessTracker fitnessTracker) {
        return fitnessTrackerDAO.save(fitnessTracker);
    }
}
