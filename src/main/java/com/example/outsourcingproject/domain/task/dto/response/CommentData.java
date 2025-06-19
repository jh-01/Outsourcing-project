package com.example.outsourcingproject.domain.task.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@JsonPropertyOrder({
        "commentId",
        "content",
        "taskId",
        "userId",
        "user",
        "createdAt",
        "updatedAt"
})
@Getter
@AllArgsConstructor
public class CommentData {

    private Long commentId;
    private String content;
    private Long taskId;
    private Integer userId;
    private CommentUserData user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
