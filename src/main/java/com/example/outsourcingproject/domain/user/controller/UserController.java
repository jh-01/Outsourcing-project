package com.example.outsourcingproject.domain.user.controller;

import com.example.outsourcingproject.domain.user.dto.*;
import com.example.outsourcingproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegisterRequestDto requestDto){
        return new ResponseEntity<>(userService.register(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword(), requestDto.getName()), HttpStatus.CREATED);
    }

    // 현재 유저 정보 조회
    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDto> userInfo(@RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(userService.userInfo(authorizationHeader), HttpStatus.OK);
    }

    // 유저 비밀번호 수정
    @PatchMapping("/api/auth/me")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UserUpdatePasswordRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(userService.updatePassword(requestDto.getNewPassword(), requestDto.getOldPassword(), authorizationHeader), HttpStatus.OK);
    }

    // 유저 탈퇴
    @DeleteMapping("/auth/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody UserWithdrawRequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(userService.withdraw(requestDto.getPassword(), authorizationHeader), HttpStatus.OK);
    }

    // 유저 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDto requestDto){
        return new ResponseEntity<>(userService.login(requestDto.getUsername(), requestDto.getPassword()), HttpStatus.OK);
    }
}
