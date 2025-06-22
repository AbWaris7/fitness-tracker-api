package com.khan.code.fitness_tracker_api.controller;


import com.khan.code.fitness_tracker_api.dao.ApplicationRepository;
import com.khan.code.fitness_tracker_api.dao.DeveloperRepository;
import com.khan.code.fitness_tracker_api.dto.ApplicationRegisterRequest;
import com.khan.code.fitness_tracker_api.dto.ApplicationResponse;
import com.khan.code.fitness_tracker_api.dto.DeveloperResponse;
import com.khan.code.fitness_tracker_api.dto.DeveloperSignupRequest;
import com.khan.code.fitness_tracker_api.entity.Application;
import com.khan.code.fitness_tracker_api.entity.Developer;
import com.khan.code.fitness_tracker_api.util.ApiKeyGenerator;
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
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/developers")
@Tag(name="Developer Rest API Endpoint", description = "Operations related to Developer")
public class DeveloperController {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder encoder;

    @Autowired
    private ApplicationRepository applicationRepository;

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


    @PostMapping("/applications/register")
    public ResponseEntity<?> registerApp(@Valid @RequestBody ApplicationRegisterRequest request, Authentication auth) {
        if (applicationRepository.existsByName(request.getName())) {
            return ResponseEntity.badRequest().body("Application name already exists");
        }

        String email = auth.getName();
        Developer dev = developerRepository.findByEmail(email).orElseThrow();

        Application app = Application.builder()
                .name(request.getName())
                .description(request.getDescription())
                .apikey(ApiKeyGenerator.generateApiKey())
                .developer(dev)
                .build();

        applicationRepository.save(app);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApplicationResponse(app.getName(), app.getApikey()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloper(@PathVariable Long id, Authentication authentication) {
        String loggedInEmail = authentication.getName();

        Developer dev = developerRepository.findById(id).orElse(null);
        if (dev == null) return ResponseEntity.notFound().build();

        if (!loggedInEmail.equals(dev.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<DeveloperResponse.ApplicationInfo> apps = dev.getApplications().stream()
                .sorted(Comparator.comparing(Application::getId).reversed())
                .map(a -> new DeveloperResponse.ApplicationInfo(
                        a.getId(), a.getName(), a.getDescription(), a.getApikey()
                ))
                .toList();

        return ResponseEntity.ok(new DeveloperResponse(dev.getId(), dev.getEmail(), apps));
    }


}

