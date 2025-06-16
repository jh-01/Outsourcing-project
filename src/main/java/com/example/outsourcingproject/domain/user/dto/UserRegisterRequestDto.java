package com.example.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 4-20자 사이여야 하고, 영문 + 숫자만 허용 가능합니다.")
    private final String username;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*?[#?!@$ %^&*-]).{8,}$", message = "비밀번호는 8자 이상이어야 하고, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    private final String password;

    @NotBlank
    @Size(min = 2, max = 50, message = "2자 이상 50자 이하로 입력해주세요.")
    private final String name;

    public UserRegisterRequestDto(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
