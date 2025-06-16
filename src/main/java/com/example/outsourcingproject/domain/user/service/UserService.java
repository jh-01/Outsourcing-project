package com.example.outsourcingproject.domain.user.service;

import com.example.outsourcingproject.domain.user.dto.UserResponseDto;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.PasswordEncoder;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.util.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 유저 회원가입
    public UserResponseDto register(String username, String email, String password, String name) {
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword, name);

        User saved = userRepository.save(user);

        return new UserResponseDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getName(), saved.getCreatedAt());
    }

    // 현재 유저 정보 조회
    public UserResponseDto userInfo(String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getCreatedAt());
    }

    // 유저 비밀번호 수정
    @Transactional
    public String updatePassword(@NotBlank String newPassword, @NotBlank String oldPassword, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 이전 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 이전 비밀번호와 새비밀번호가 동일하다면 예외
        if(passwordEncoder.matches(newPassword, oldPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 새로운 비밀번호가 동일합니다.");
        }

        String updateNewPassword = passwordEncoder.encode(newPassword);

        user.updatePassword(updateNewPassword);

        return "비밀번호가 수정되었습니다";

    }

    // 유저 탈퇴
    @Transactional
    public String withdraw(String password, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());

        return "회원 탈퇴가 완료되었습니다.";
    }

    // 유저 로그인
    public String login(String username, String password) {

        // 사용자가가 존재하는지 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 이미 탈퇴한 사용자인지 확인
        if(user.isDeleted()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이미 탈퇴한 아이디입니다.");
        }

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(user.getId(), user.getEmail());
    }
}
