package com.example.outsourcingproject.domain.user.service;

import com.example.outsourcingproject.domain.user.dto.UserResponseDto;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.domain.user.entity.UserRole;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.security.PasswordEncoder;
import com.example.outsourcingproject.global.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.outsourcingproject.domain.user.entity.UserRole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private String username;
    private String email;
    private String password;
    private String name;

    @BeforeEach
    void setUp() {
        username = "name";
        email = "name@example.com";
        password = "Password!1";
        name = "이름";
    }

    @Test
    @DisplayName("유저 회원가입 성공")
    void REGISTER_SUCCESS(){
        // given
        String encodedPassword = passwordEncoder.encode(password);
        User savedUser = new User(username, email, encodedPassword, name, UserRole.USER);
        savedUser.setId(1);
        savedUser.setCreatedAt(LocalDateTime.now());
        given(userRepository.save(any())).willReturn(savedUser);

        // when
        ApiResponse<UserResponseDto> response = userService.register(username, email, password, name);

        // then
        assertTrue(response.isSuccess());
        assertEquals("회원가입이 완료되었습니다.", response.getMessage());
    }

    @Test
    @DisplayName("유저 회원가입 시 이메일 중복")
    void REGISTER_FAIL_EMAIL_DUPLICATE(){
        // given
        given(userRepository.existsByEmail("name@example.com")).willReturn(true);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.register(username, email, password, name));
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유저 회원가입 시 아이디 중복")
    void REGISTER_FAIL_USERNAME_DUPLICATE(){
        // given
        given(userRepository.existsByUsername("name")).willReturn(true);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.register(username, email, password, name));
        assertEquals("이미 존재하는 아이디입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("현재 유저 정보 조회 성공")
    void USER_INFO_FOUND_SUCCESS(){
        // given
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // when
        ApiResponse<UserResponseDto> response = userService.userInfo(username);

        // then
        assertTrue(response.isSuccess());
        assertEquals("사용자 정보를 조회했습니다.", response.getMessage());

    }

    @Test
    @DisplayName("현재 유저 정보 조회 실패")
    void USER_INFO_FOUND_FAIL(){
        // given
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.userInfo(username));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("유저 목록 조회 성공")
    void GET_USERS_SUCCESS(){
        // given
        List<User> Users = List.of(
                new User("name1", "name1@example.com", "Password!1", "이름1", USER),
                new User("name2", "name2@example.com", "Password!2", "이름2", USER)
        );
        given(userRepository.findAll()).willReturn(Users);

        // when
        ApiResponse<List<UserResponseDto>> response = userService.getUsers();

        // then
        assertTrue(response.isSuccess());
        assertEquals("유저 목록 조회 성공", response.getMessage());

        UserResponseDto dto1 = response.getData().get(0);
        assertEquals("name1", dto1.getUsername());
        assertEquals("name1@example.com", dto1.getEmail());

        UserResponseDto dto2 = response.getData().get(1);
        assertEquals("name2", dto2.getUsername());
        assertEquals("name2@example.com", dto2.getEmail());

    }

    @Test
    @DisplayName("유저 비밀번호 수정 성공")
    void USER_UPDATE_PASSWORD_SUCCESS(){
        // given
        String username = "name";
        String oldPassword = "Password!1";
        String newPassword = "Password!12";

        User user = new User(username, "test@example.com", passwordEncoder.encode(oldPassword), "이름", USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(true);
        given(passwordEncoder.encode(newPassword)).willReturn("Password!12");

        // when
        ApiResponse<?> response = userService.updatePassword(newPassword, oldPassword, username);

        // then
        assertTrue(response.isSuccess());
        assertEquals("비밀번호가 수정되었습니다.", response.getMessage());
    }

    @Test
    @DisplayName("유저 비밀번호 수정시 기존 비밀번호 불일치")
    void USER_UPDATE_PASSWORD_FAIL_WHEN_OLD_PASSWORD_WRONG(){
        // given
        String username = "name";
        String oldPassword = "Password!1";
        String newPassword = "Password!12";

        User user = new User(username, "test@example.com", passwordEncoder.encode(oldPassword), "이름", USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.updatePassword(newPassword, oldPassword, username));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유저 비밀번호 수정시 기존 비밀번호와 새 비밀번호 동일")
    void USER_UPDATE_PASSWORD_FAIL_WHEN_NEWPASSWORD_IS_SAME_AS_OLDPASSWORD(){
        // given
        String username = "name";
        String oldPassword = "Password!1";
        String newPassword = "Password!1";

        User user = new User(username, "test@example.com", passwordEncoder.encode(oldPassword), "이름", USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(true);
        given(passwordEncoder.matches(newPassword, user.getPassword())).willReturn(true);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.updatePassword(newPassword, oldPassword, username));
        assertEquals("기존 비밀번호와 새로운 비밀번호가 동일합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유저 탈퇴 성공")
    void USER_WITHDRAW_SUCCESS(){
        // given
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);

        // when
        ApiResponse<?> response = userService.withdraw(password, username);

        // then
        assertTrue(response.isSuccess());
        assertEquals("회원탈퇴가 완료되었습니다.", response.getMessage());
        assertTrue(user.isDeleted());
        assertNotNull(user.getDeletedAt());
    }

    @Test
    @DisplayName("유저 탈퇴시 비밀번호 불일치")
    void USER_WITHDRAW_FAIL_WHEN_PASSWORD_WRONG(){
        // given
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.withdraw(password, username));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());
        assertFalse(user.isDeleted());
        assertNull(user.getDeletedAt());
    }

    @Test
    @DisplayName("유저 로그인 성공")
    void USER_LOGIN_SUCCESS(){
        // given
        int userId = 3;
        String token = "alsdfjalsfjasdkfjslkafjklfjsdfasdfafaf";
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        user.setId(userId);
        user.setDeleted(false);

        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);
        given(jwtUtil.createToken(userId, username, UserRole.USER)).willReturn(token);

        // when
        ApiResponse<?> response = userService.login(username, password);

        // then
        assertTrue(response.isSuccess());
        assertEquals("로그인이 완료되었습니다.", response.getMessage());
    }

    @Test
    @DisplayName("유저 로그인시 아이디 없음")
    void USER_LOGIN_FAIL_USERNAME_NOT_FOUND(){
        // given
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.login(username, password));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("탈퇴한 사용자가 로그인 했을 경우")
    void USER_LOGIN_FAIL_WITHDRAW_USER(){
        // given
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        user.setDeleted(true);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.login(username, password));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유저 로그인 시 비밀번호 불일치")
    void USER_LOGIN_FAIL_INVALID_PASSWORD(){
        // given
        User user = new User(username, email, passwordEncoder.encode(password), name, USER);
        user.setDeleted(false);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(password, user.getPassword())).willReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.login(username, password));
        assertEquals("잘못된 사용자명 또는 비밀번호입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("존재하는 유저인 경우")
    void EXISTS_BY_ID_IS_SUCCESS() {
        // given
        Long userId = 3L;
        given(userRepository.existsById(userId)).willReturn(true);

        // when
        boolean result = userService.existsById(userId);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("존재하지 않는 유저인 경우")
    void EXISTS_BY_ID_IS_FAIL() {
        // given
        Long userId = 3L;
        given(userRepository.existsById(userId)).willReturn(false);

        // when
        boolean result = userService.existsById(userId);

        // then
        assertFalse(result);
    }

}