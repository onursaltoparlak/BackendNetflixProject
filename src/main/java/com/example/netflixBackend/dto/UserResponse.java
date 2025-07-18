package com.example.netflixBackend.dto;

import com.example.netflixBackend.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
} 