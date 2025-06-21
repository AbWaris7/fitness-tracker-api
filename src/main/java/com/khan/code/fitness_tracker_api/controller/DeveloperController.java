package com.khan.code.fitness_tracker_api.controller;


import com.khan.code.fitness_tracker_api.dao.DeveloperRepository;
import com.khan.code.fitness_tracker_api.dto.DeveloperResponse;
import com.khan.code.fitness_tracker_api.dto.DeveloperSignupRequest;
import com.khan.code.fitness_tracker_api.entity.Developer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/developers")
@Tag(name="Developer Rest API Endpoint", description = "Operations related to Developer")
public class DeveloperController {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public DeveloperController(DeveloperRepository developerRepository, PasswordEncoder encoder) {
        this.developerRepository = developerRepository;
        this.encoder = encoder;
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up the Developer", description = "register the developer if not exist account ")
    public ResponseEntity<?> signup(@Valid @RequestBody DeveloperSignupRequest request) {
        if (developerRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        Developer dev = Developer.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        Developer saved = developerRepository.save(dev);
        URI location = URI.create("/api/developers/" + saved.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get  Developer by Id", description = "Retrieve a record based on developer id ")
    public ResponseEntity<?> getDeveloper(@PathVariable Long id, Authentication authentication) {
        String loggedInEmail = authentication.getName();

        Developer dev = developerRepository.findById(id).orElse(null);
        if (dev == null) return ResponseEntity.notFound().build();

        if (!loggedInEmail.equals(dev.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new DeveloperResponse(dev.getId(), dev.getEmail()));
    }
}

