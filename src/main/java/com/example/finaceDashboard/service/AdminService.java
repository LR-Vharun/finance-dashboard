package com.example.finaceDashboard.service;

import com.example.finaceDashboard.entity.Role;
import com.example.finaceDashboard.entity.User;
import com.example.finaceDashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserRole(Long userId, String roleStr) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Protect ADMIN accounts — cannot change an admin's role
        if (user.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Cannot change the role of an ADMIN account.");
        }

        Role newRole;
        try {
            newRole = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleStr);
        }

        // Only USER ↔ USER_PRO transitions are allowed
        if (newRole == Role.ADMIN) {
            throw new IllegalArgumentException("Cannot promote a user to ADMIN via this API.");
        }

        user.setRole(newRole);
        return userRepository.save(user);
    }
}
