package com.example.outsourcingproject.domain.user.controller;

import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.user.dto.*;
import com.example.outsourcingproject.domain.user.service.UserService;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/auth/register")
    public ApiResponse<UserResponseDto> register(@Valid @RequestBody UserRegisterRequestDto requestDto){
        return userService.register(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword(), requestDto.getName());
    }

    // 현재 유저 정보 조회
    @GetMapping("/users/me")
    public ApiResponse<UserResponseDto> userInfo(@AuthenticationPrincipal User user){
        return userService.userInfo(user.getUsername());
    }

    // 유저 비밀번호 수정
    @PatchMapping("/auth/me")
    public ApiResponse<?> updatePassword(@Valid @RequestBody UserUpdatePasswordRequestDto requestDto, @AuthenticationPrincipal User user){
        return userService.updatePassword(requestDto.getNewPassword(), requestDto.getOldPassword(), user.getUsername());
    }

    // 유저 탈퇴
    @PostMapping("/auth/withdraw")
    public ApiResponse<?> withdraw(@Valid @RequestBody UserWithdrawRequestDto requestDto, @AuthenticationPrincipal User user){
        return userService.withdraw(requestDto.getPassword(), user.getUsername());
    }

    // 유저 로그인
    @LogWrite(type = LogType.USER_LOGGED_IN)
    @PostMapping("/auth/login")
    public ApiResponse<?> login(@Valid @RequestBody UserLoginRequestDto requestDto){
        return userService.login(requestDto.getUsername(), requestDto.getPassword());
    }
}
