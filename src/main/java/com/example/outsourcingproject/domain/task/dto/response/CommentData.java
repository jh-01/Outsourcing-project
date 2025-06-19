package com.example.outsourcingproject.domain.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentData {

    private Long commentId;
    private String content;
    private Long TaskId;
    private Integer UserId;
    private CommentUserData user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
