package com.khan.code.fitness_tracker_api.dao;

import com.khan.code.fitness_tracker_api.entity.FitnessTracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FitnessTrackerDAOImpl implements FitnessTrackerDAO {

    private final EntityManager entityManager;

    @Autowired
    public FitnessTrackerDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<FitnessTracker> findAll() {

        TypedQuery<FitnessTracker> query = entityManager.createQuery("from FitnessTracker", FitnessTracker.class);

        return query.getResultList();
    }
}
