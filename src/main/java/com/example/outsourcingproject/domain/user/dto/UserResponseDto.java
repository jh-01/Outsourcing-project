package com.example.outsourcingproject.domain.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final int id;

    private final String username;

    private final String email;

    private final String name;

    private final LocalDateTime createdAt;


    public UserResponseDto(int id, String username, String email, String name, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }
}


