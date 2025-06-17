package com.example.outsourcingproject.domain.user.service;

import com.example.outsourcingproject.domain.user.dto.UserResponseDto;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.security.PasswordEncoder;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import com.example.outsourcingproject.global.util.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 유저 회원가입
    public ApiResponse<UserResponseDto> register(String username, String email, String password, String name) {
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        }

        if(userRepository.existsByUsername(username)){
            throw new CustomException(ErrorType.DUPLICATE_USERNAME);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword, name);

        User saved = userRepository.save(user);

        return ApiResponse.createSuccess("회원가입이 완료되었습니다.", new UserResponseDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getName(), saved.getCreatedAt()));
    }

    // 현재 유저 정보 조회
    public ApiResponse<UserResponseDto> userInfo(String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_CREDENTIALS));

        return ApiResponse.createSuccess("사용자 정보를 조회했습니다.", new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getCreatedAt()));
    }

    // 유저 비밀번호 수정
    @Transactional
    public ApiResponse<?> updatePassword(@NotBlank String newPassword, @NotBlank String oldPassword, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_CREDENTIALS));

        // 이전 비밀번호가 맞는지 확인
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException(ErrorType.INVALID_CREDENTIALS);
        }

        // 이전 비밀번호와 새비밀번호가 동일하다면 예외
        if(passwordEncoder.matches(newPassword, oldPassword)){
            throw new CustomException(ErrorType.PASSWORD_SAME);
        }

        String updateNewPassword = passwordEncoder.encode(newPassword);

        user.updatePassword(updateNewPassword);

        return ApiResponse.createSuccessWithNoContent("비밀번호가 수정되었습니다.");

    }

    // 유저 탈퇴
    @Transactional
    public ApiResponse<?> withdraw(String password, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);
        String email = jwtUtil.extractClaims(token).get("email", String.class);

        // 사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_CREDENTIALS));

        // 비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorType.INVALID_CREDENTIALS);
        }

        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());

        return ApiResponse.createSuccessWithNoContent("회원탈퇴가 완료되었습니다.");
    }

    // 유저 로그인
    public ApiResponse<?> login(String username, String password) {

        // 사용자가가 존재하는지 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_CREDENTIALS));

        // 이미 탈퇴한 사용자인지 확인
        if(user.isDeleted()){
            throw  new CustomException(ErrorType.INVALID_CREDENTIALS);
        }

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorType.INVALID_CREDENTIALS);
        }

        return ApiResponse.createSuccess("로그인이 완료되었습니다.", jwtUtil.createToken(user.getId(), user.getEmail()));
    }

}
