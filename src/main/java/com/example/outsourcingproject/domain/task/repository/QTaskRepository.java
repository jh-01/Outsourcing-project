package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.dashboard.dto.TaskOutline;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QTaskRepository {
    TaskResponse findTaskById(Long id);
    Page<TaskResponse> findTasks(TaskReadRequest request);
    TaskOutline findDashboard();
    List<TaskResponse> findTasksByUserId(Long userId);
}
