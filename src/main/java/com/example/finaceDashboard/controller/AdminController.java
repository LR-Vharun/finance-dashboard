package com.example.finaceDashboard.controller;

import com.example.finaceDashboard.dto.AdminUserDetailDto;
import com.example.finaceDashboard.dto.RoleUpdateDto;
import com.example.finaceDashboard.entity.User;
import com.example.finaceDashboard.service.AdminService;
import com.example.finaceDashboard.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminController — accessible only to ADMIN role.
 * Security enforcement managed by Spring Security (SecurityConfig).
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TransactionService transactionService;

    // ── GET /api/admin/users — list all users ──────────────────────
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // ── GET /api/admin/users/{id} — user detail + transactions ─────
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id) {
        try {
            AdminUserDetailDto detail = transactionService.getUserDetail(id);
            return ResponseEntity.ok(detail);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── PUT /api/admin/users/{id}/role — promote / demote ──────────
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Long id,
            @RequestBody RoleUpdateDto dto) {
        try {
            User updated = adminService.updateUserRole(id, dto.getRole());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
