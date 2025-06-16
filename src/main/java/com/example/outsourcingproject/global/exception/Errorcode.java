package com.example.outsourcingproject.global.exception;

import org.springframework.http.HttpStatus;

public enum Errorcode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task 가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String messages;

    Errorcode(HttpStatus httpStatus, String messages) {
        this.httpStatus = httpStatus;
        this.messages = messages;
    }

    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    public String getMessages() {
        return this.messages;
    }

}
