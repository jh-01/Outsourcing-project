package com.example.outsourcingproject.domain.task.dto.response;

import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.user.dto.AssigneeResponse;
import com.example.outsourcingproject.global.log.LoggableResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class TaskResponse implements LoggableResponse {
    // id 추가됨
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;
    private Status status;
    private Long assigneeId;
    private AssigneeResponse assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @QueryProjection
    @Builder
    public TaskResponse(Long id, String title, String description, LocalDateTime dueDate,
                        Priority priority, Status status, Long assigneeId, AssigneeResponse assignee,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.assigneeId = assigneeId;
        this.assignee = assignee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonIgnore
    @Override
    public int getTargetId() {
        return id.intValue();
    }
}
