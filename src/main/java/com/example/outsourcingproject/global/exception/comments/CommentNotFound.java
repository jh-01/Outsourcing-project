package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.ErrorType;

public class CommentNotFound extends RuntimeException {
    private ErrorType errorType;

    public CommentNotFound(ErrorType errorType) {
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
