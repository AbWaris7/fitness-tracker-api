package com.khan.code.fitness_tracker_api.dao;

import com.khan.code.fitness_tracker_api.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByEmail(String email);
}
