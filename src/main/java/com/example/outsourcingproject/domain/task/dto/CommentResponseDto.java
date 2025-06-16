package com.example.outsourcingproject.domain.task.dto;

import com.example.outsourcingproject.domain.task.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {

    private final boolean isSuccess;
    private final String messages;
    private final CommentResponseData data;
    private final LocalDateTime timestamp;

    public CommentResponseDto(boolean isSuccess, String messages, CommentResponseData data, LocalDateTime timestamp) {
        this.isSuccess = isSuccess;
        this.messages = messages;
        this.data = data;
        this.timestamp = timestamp;

    }
}
