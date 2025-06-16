package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 태스크 생성
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);

        return ResponseEntity.ok().body(taskResponse);
    }

    // 태스크 목록 조회

    // 태스크 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long taskId
    ) {
        TaskResponse taskResponse = taskService.modifyTask(taskRequest, taskId);

        return ResponseEntity.ok().body(taskResponse);
    }
    // 태스크 상태 변경
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @Valid @RequestBody TaskStatusUpdateRequest statusUpdateRequest,
            @PathVariable Long taskId
    ) {
        TaskResponse taskResponse = taskService.modifyTaskStatus(statusUpdateRequest, taskId);

        return ResponseEntity.ok().body(taskResponse);
    }

    // 태스크 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId
    ) {
        taskService.softDelete(taskId);

        return ResponseEntity.ok().build();
    }

}
