package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
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
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest
    ) {

        Long userId = (Long) request.getAttribute("id");
        TaskResponse taskResponse = taskService.createTask(taskRequest, userId);

        return ResponseEntity.ok().body(taskResponse);
    }

    // 태스크 목록 조회

    // 태스크 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long taskId
    ) {
        Long userId = (Long) request.getAttribute("id");
        TaskResponse taskResponse = taskService.modifyTask(taskRequest, taskId, userId);

        return ResponseEntity.ok().body(taskResponse);
    }
    // 태스크 상태 변경
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            HttpServletRequest request,
            @Valid @RequestBody TaskStatusUpdateRequest statusUpdateRequest,
            @PathVariable Long taskId
    ) {
        Long userId = (Long) request.getAttribute("id");
        TaskResponse taskResponse = taskService.modifyTaskStatus(statusUpdateRequest, taskId, userId);

        return ResponseEntity.ok().body(taskResponse);
    }

    // 태스크 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            HttpServletRequest request,
            @PathVariable Long taskId
    ) {
        Long userId = (Long) request.getAttribute("id");
        taskService.softDelete(taskId, userId);

        return ResponseEntity.ok().build();
    }

}
