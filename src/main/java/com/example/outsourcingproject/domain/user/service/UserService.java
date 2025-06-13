package com.example.outsourcingproject.domain.user.service;

import com.example.outsourcingproject.domain.user.dto.RegisterResponseDto;
import com.example.outsourcingproject.global.PasswordEncoder;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RegisterResponseDto register;

    public RegisterResponseDto register(String username, String email, String password, String name) {
        return null;
    }
}
