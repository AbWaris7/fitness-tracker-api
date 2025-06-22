package com.khan.code.fitness_tracker_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // For Postman/curl
                .headers(headers -> headers.disable()) // To allow H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/developers/signup").permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/developers/**").authenticated()
                        .anyRequest().permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/applications/register").authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Enables Basic Auth
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

