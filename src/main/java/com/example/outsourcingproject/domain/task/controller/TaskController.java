package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import com.example.outsourcingproject.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 태스크 생성
    @PostMapping
    public ApiResponse<TaskResponse> createTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskResponse taskResponse = taskService.createTask(taskRequest, userId);

        return ApiResponse.createSuccess("태스크가 성공적으로 생성되었습니다.", taskResponse);
    }

    // 태스크 목록 조회

    // 태스크 수정
    @PutMapping("/{taskId}")
    public ApiResponse<TaskResponse> updateTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long taskId
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskResponse taskResponse = taskService.modifyTask(taskRequest, taskId, userId);

        return ApiResponse.createSuccess("태스크가 성공적으로 수정되었습니다.", taskResponse);
    }
    // 태스크 상태 변경
    @PatchMapping("/{taskId}/status")
    public ApiResponse<TaskResponse> updateTaskStatus(
            HttpServletRequest request,
            @Valid @RequestBody TaskStatusUpdateRequest statusUpdateRequest,
            @PathVariable Long taskId
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskResponse taskResponse = taskService.modifyTaskStatus(statusUpdateRequest, taskId, userId);

        return ApiResponse.createSuccess("태스크 상태가 성공적으로 수정되었습니다.", taskResponse);
    }

    // 태스크 삭제
    @DeleteMapping("/{taskId}")
    public ApiResponse<?> deleteTask(
            HttpServletRequest request,
            @PathVariable Long taskId
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        taskService.softDelete(taskId, userId);

        return ApiResponse.createSuccessWithNoContent("삭제 완료");
    }

}
