package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.Errorcode;

public class TaskNotFound extends RuntimeException {
    private Errorcode errorcode;

    public TaskNotFound(Errorcode errorcode) {
        super(errorcode.getMessages());
        this.errorcode = errorcode;
    }

    public Errorcode getErrorcode() {
        return errorcode;
    }
}
