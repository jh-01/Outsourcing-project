package com.example.outsourcingproject.domain.task.dto.request;

import com.example.outsourcingproject.domain.task.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskReadRequest {
    private int page = 0;
    private int size = 10;

    private String title;
    private String description;
    private Status status;
    private Long assigneeId;

    public TaskReadRequest(Long assigneeId) {
        this.title = null;
        this.description = null;
        this.status = null;
        this.assigneeId = assigneeId;
    }
}
