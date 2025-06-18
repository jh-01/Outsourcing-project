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

    // 공통 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생했습니다"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),

    // 유저 관련 에러
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 사용자명 또는 비밀번호입니다"),
    PASSWORD_SAME(HttpStatus.UNAUTHORIZED, "기존 비밀번호와 새로운 비밀번호가 동일합니다."),

    // 태스크 관련 에러
    INVALID_DEADLINE(HttpStatus.BAD_REQUEST, "마감시간은 현재 시간보다 이전일 수 없습니다."),
    INVALID_TASK_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "TODO -> IN_PROGRESS -> DONE 순서로만 수정 할 수 있습니다."),
    TASK_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태스크입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),

    // 로그 관련 에러
    LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 로그를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
