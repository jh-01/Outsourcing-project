package com.example.outsourcingproject.domain.task.dto.response;

import com.example.outsourcingproject.domain.task.entity.Priority;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String managerName;
    private String generatorName;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime deadline;
    private Status status;
    private LocalDateTime startAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @QueryProjection
    public TaskResponse(Long id, String managerName, String generatorName, String title, String description, Priority priority, LocalDateTime deadline, Status status, LocalDateTime startAt, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.managerName = managerName;
        this.generatorName = generatorName;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.startAt = startAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
