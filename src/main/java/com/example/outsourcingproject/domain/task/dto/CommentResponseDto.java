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

    // Comment 에서 CommentResponseDto 로 변환
    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(
                true,
                "조회 성공!",
                new CommentResponseData(comment.getTask().getId(), comment.getUser().getName(), comment.getContents()),
                comment.getCreatedAt());
    }

}
