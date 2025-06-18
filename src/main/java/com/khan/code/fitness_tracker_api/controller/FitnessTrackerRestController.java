package com.khan.code.fitness_tracker_api.controller;

import com.khan.code.fitness_tracker_api.dao.FitnessTrackerDAO;
import com.khan.code.fitness_tracker_api.entity.FitnessTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public FitnessTracker save(@RequestBody FitnessTracker fitnessTracker) {
        return fitnessTrackerDAO.save(fitnessTracker);
    }


}
