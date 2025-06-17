package com.example.outsourcingproject.domain.user.dto;

import com.example.outsourcingproject.domain.user.entity.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final int id;

    private final String username;

    private final String email;

    private final String name;

    private final UserRole role;

    private final LocalDateTime createdAt;


    public UserResponseDto(int id, String username, String email, String name, UserRole role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }
}


