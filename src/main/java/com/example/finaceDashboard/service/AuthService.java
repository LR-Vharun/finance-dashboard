package com.example.finaceDashboard.service;

import com.example.finaceDashboard.dto.LoginResponseDto;
import com.example.finaceDashboard.entity.Role;
import com.example.finaceDashboard.entity.User;
import com.example.finaceDashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "Email already exists";
        }
        // Default role to USER if not provided
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        // Encode password before saving (even if currently NoOp)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public Object login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Use passwordEncoder.matches for security best practice
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new LoginResponseDto("Login successful", user.getId(), user.getEmail(), user.getRole().name(), user.getName());
            }
        }
        return "Invalid email or password";
    }
}
