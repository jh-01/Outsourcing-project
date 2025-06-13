package com.example.outsourcingproject.domain.user.dto;

import lombok.Getter;

@Getter
public class RegisterResponseDto {

    private final String id;

    private final String username;

    private final String email;

    private final String name;

    private final String createdAt;


    public RegisterResponseDto(String id, String username, String email, String name, String createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }
}


