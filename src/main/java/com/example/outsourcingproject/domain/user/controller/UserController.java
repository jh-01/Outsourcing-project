package com.example.outsourcingproject.domain.user.controller;

import com.example.outsourcingproject.domain.user.dto.RegisterRequestDto;
import com.example.outsourcingproject.domain.user.dto.RegisterResponseDto;
import com.example.outsourcingproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto requestDto){
        return new ResponseEntity<>(userService.register(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword(), requestDto.getName()), HttpStatus.OK);
    }

    // 현재 유저 정보 조회

    // 유저 비밀번호 수정

    // 유저 탈퇴

    // 유저 로그인

    // 유저 로그아웃
}
