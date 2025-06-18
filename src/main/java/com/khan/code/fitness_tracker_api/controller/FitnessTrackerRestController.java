package com.khan.code.fitness_tracker_api.controller;

import com.khan.code.fitness_tracker_api.dao.FitnessTrackerDAO;
import com.khan.code.fitness_tracker_api.entity.FitnessTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class FitnessTrackerRestController {

    private final FitnessTrackerDAO fitnessTrackerDAO;

    @Autowired
    public FitnessTrackerRestController(FitnessTrackerDAO fitnessTrackerDAO) {
        this.fitnessTrackerDAO = fitnessTrackerDAO;
    }

    @GetMapping()
    public List<FitnessTracker> findAll() {
        return fitnessTrackerDAO.findAll();
    }
}
