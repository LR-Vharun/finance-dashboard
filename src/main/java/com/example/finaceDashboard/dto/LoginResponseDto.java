package com.example.finaceDashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String message;
    private Long userId;
    private String email;
    private String role;
    private String name;
}
