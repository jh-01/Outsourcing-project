package com.example.outsourcingproject.domain.user.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDto {

    private final String username;

    private final String email;

    private final String password;

    private final String name;

    public RegisterRequestDto(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
