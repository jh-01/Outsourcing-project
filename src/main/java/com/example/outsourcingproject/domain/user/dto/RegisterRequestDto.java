package com.example.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*?[#?!@$ %^&*-]).{8,}$", message = "비밀번호는 8자 이상이어야 하고, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    private final String password;

    @NotBlank
    private final String name;

    public RegisterRequestDto(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
