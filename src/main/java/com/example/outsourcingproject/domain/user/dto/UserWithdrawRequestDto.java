package com.example.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserWithdrawRequestDto {

    @NotBlank
    private final String password;

    public UserWithdrawRequestDto(String password) {
        this.password = password;
    }
}
