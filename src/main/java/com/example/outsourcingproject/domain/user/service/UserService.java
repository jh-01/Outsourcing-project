package com.example.outsourcingproject.domain.user.service;

import com.example.outsourcingproject.domain.user.dto.RegisterResponseDto;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.PasswordEncoder;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDto register(String username, String email, String password, String name) {

        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword, name);

        User saved = userRepository.save(user);

        return new RegisterResponseDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getName(), saved.getCreatedAt());
    }
}
