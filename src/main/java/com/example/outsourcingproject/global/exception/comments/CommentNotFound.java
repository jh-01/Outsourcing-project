package com.example.outsourcingproject.global.exception.comments;

import com.example.outsourcingproject.global.exception.Errorcode;

public class CommentNotFound extends RuntimeException {
    private Errorcode errorcode;

    public CommentNotFound(Errorcode errorcode) {
        super(errorcode.getMessages());
        this.errorcode = errorcode;
    }

    public Errorcode getErrorcode() {
        return errorcode;
    }
}
