package com.khan.code.fitness_tracker_api.dao;

import com.khan.code.fitness_tracker_api.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByName(String name);
}
