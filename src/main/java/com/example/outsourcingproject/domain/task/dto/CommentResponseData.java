package com.example.outsourcingproject.domain.task.dto;

import lombok.Getter;

@Getter
public class CommentResponseData {

    private final Long task_id;
    private final String userName;
    private final String contents;

    public CommentResponseData(Long task_id, String userName, String contents) {
        this.task_id = task_id;
        this.userName = userName;
        this.contents = contents;
    }

}
