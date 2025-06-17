package com.example.outsourcingproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    /**
     * 200 OK: 성공
     * 400 Bad Request: 잘못된 요청 (유효성 검사 실패)
     * 401 Unauthorized: 인증 실패
     * 403 Forbidden: 권한 없음
     * 404 Not Found: 리소스 없음
     * 406 CONFLICT: 요청이 서버의 현재 상태와 충돌
     * 500 Internal Server Error: 서버 오류
     */

    // 중복된 username
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다"),
    // 중복된 email
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    // 잘못된 인증 정보
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 사용자명 또는 비밀번호입니다"),
    // 기존 비밀번호와 새로운 비밀번호 일치
    PASSWORD_SAME(HttpStatus.UNAUTHORIZED, "기존 비밀번호와 새로운 비밀번호가 동일합니다."),
    // 서버 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생했습니다"),
    // 잘못된 요청 형식
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    // 로그를 찾지 못함
    LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 로그를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
