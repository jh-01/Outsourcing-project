package com.example.outsourcingproject.domain.task.dto.response;

import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.user.dto.SimpleAssigneeResponse;
import com.example.outsourcingproject.global.log.LoggableResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class TaskStatusResponse implements LoggableResponse {
    // id 추가됨
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Long assigneeId;
    private SimpleAssigneeResponse assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    @QueryProjection
    public TaskStatusResponse(Long id, String title, String description, Status status, Priority priority,
                              Long assigneeId, SimpleAssigneeResponse assignee, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assigneeId = assigneeId;
        this.assignee = assignee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
    }

    @Override
    public int getTargetId() {
        return id.intValue();
    }
}
