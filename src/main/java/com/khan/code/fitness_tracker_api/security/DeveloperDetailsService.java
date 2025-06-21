package com.khan.code.fitness_tracker_api.security;

import com.khan.code.fitness_tracker_api.dao.DeveloperRepository;
import com.khan.code.fitness_tracker_api.entity.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeveloperDetailsService implements UserDetailsService {

    @Autowired
    private DeveloperRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Developer dev = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Developer not found"));

        return User.withUsername(dev.getEmail())
                .password(dev.getPassword())
                .roles("DEVELOPER")
                .build();
    }
}