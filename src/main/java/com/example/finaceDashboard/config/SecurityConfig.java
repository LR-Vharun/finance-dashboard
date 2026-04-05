package com.example.finaceDashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in stateless API
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults()) // Enable httpBasic
            .authorizeHttpRequests(auth -> auth
                // 1. PUBLIC ACCESS
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/", "/index.html", "/*.html", "/style.css", "/script.js", "/favicon.ico").permitAll()

                // 2. ADMIN ONLY - Restricted deletions and admin dashboard
                .requestMatchers(HttpMethod.DELETE, "/api/transactions/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // 3. USER_PRO + ADMIN - Summaries are only for PRO and ADMIN
                // (Global summary /api/dashboard/summary is no longer allowed for anyone)
                .requestMatchers("/api/dashboard/total-*", "/api/dashboard/balance").hasAnyRole("USER_PRO", "ADMIN")

                // 4. AUTHENTICATED ACCESS - Viewing and creating transactions
                .requestMatchers("/api/transactions/**").authenticated()
                .anyRequest().denyAll() // Strict denial for any other path
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Keeping it simple with NoOp to support current plain-text passwords (e.g., "1234")
        return NoOpPasswordEncoder.getInstance();
    }
}