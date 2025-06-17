package com.example.outsourcingproject.domain.user.controller;

import com.example.outsourcingproject.domain.user.dto.*;
import com.example.outsourcingproject.domain.user.service.UserService;
import com.example.outsourcingproject.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<UserResponseDto> userInfo(@RequestHeader("Authorization") String authorizationHeader){
        return userService.userInfo(authorizationHeader);
    }

    // 유저 비밀번호 수정
    @PatchMapping("/auth/me")
    public ApiResponse<?> updatePassword(@Valid @RequestBody UserUpdatePasswordRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader){
        return userService.updatePassword(requestDto.getNewPassword(), requestDto.getOldPassword(), authorizationHeader);
    }

    // 유저 탈퇴
    @PostMapping("/auth/withdraw")
    public ApiResponse<?> withdraw(@Valid @RequestBody UserWithdrawRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader){
        return userService.withdraw(requestDto.getPassword(), authorizationHeader);
    }

    // 유저 로그인
    @PostMapping("/auth/login")
    public ApiResponse<?> login(@Valid @RequestBody UserLoginRequestDto requestDto){
        return userService.login(requestDto.getUsername(), requestDto.getPassword());
    }
}
