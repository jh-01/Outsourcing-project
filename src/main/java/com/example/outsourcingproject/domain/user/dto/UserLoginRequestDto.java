package com.example.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    public UserLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

