package com.example.finaceDashboard.controller;

import com.example.finaceDashboard.dto.LoginDto;
import com.example.finaceDashboard.entity.User;
import com.example.finaceDashboard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String response = authService.register(user);
        if (response.equals("Email already exists")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Object response = authService.login(loginDto.getEmail(), loginDto.getPassword());
        if (response instanceof String) {
            return ResponseEntity.badRequest().body(response); // Invalid email or password
        }
        return ResponseEntity.ok(response); // Returns the User object directly on success
    }
}
