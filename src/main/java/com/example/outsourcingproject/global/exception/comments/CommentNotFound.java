package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;

public class CommentNotFound extends CustomException {
    private ErrorType errorType;

    public CommentNotFound(ErrorType errorType) {
        super(errorType.COMMENT_NOT_FOUND);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
