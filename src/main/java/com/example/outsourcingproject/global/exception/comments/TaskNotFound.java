package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.ErrorType;

public class TaskNotFound extends RuntimeException {
    private ErrorType errorType;

    public TaskNotFound(ErrorType errorType) {
        super(errorType.getErrorMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
