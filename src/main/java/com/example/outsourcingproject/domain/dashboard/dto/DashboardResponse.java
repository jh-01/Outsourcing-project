package com.example.outsourcingproject.domain.dashboard.dto;

import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import java.util.List;

@Getter
public class DashboardResponse {
    private TaskOutline taskOutline;
    private List<TaskResponse> taskList;

    @QueryProjection
    public DashboardResponse(TaskOutline taskOutline, List<TaskResponse> taskList) {
        this.taskOutline = taskOutline;
        this.taskList = taskList;
    }
}
