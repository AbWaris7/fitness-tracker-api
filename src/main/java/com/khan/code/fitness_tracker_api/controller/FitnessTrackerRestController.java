package com.khan.code.fitness_tracker_api.controller;
import com.khan.code.fitness_tracker_api.entity.FitnessTracker;
import com.khan.code.fitness_tracker_api.service.FitnessTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracker")
@Tag(name="Fitness Tracker Rest API Endpoint", description = "Operations related to Fitness Tracker")
public class FitnessTrackerRestController {

    private final FitnessTrackerService fitnessTrackerService;

    public FitnessTrackerRestController(FitnessTrackerService fitnessTrackerService) {
        this.fitnessTrackerService = fitnessTrackerService;
    }

    @Operation(summary = "Get all Fitness Tracker", description = "Retrieve a list of all users details ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<FitnessTracker> findAll() {
        return fitnessTrackerService.findAll();
    }

    @Operation(summary = "Create a new Fitness Tracker", description = "Add a new user details")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public FitnessTracker save(@Valid @RequestBody FitnessTracker fitnessTracker) {
        return fitnessTrackerService.save(fitnessTracker);
    }


}
