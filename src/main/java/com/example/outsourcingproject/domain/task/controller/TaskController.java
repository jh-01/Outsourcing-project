package com.example.outsourcingproject.domain.task.controller;

import com.example.outsourcingproject.domain.log.entity.LogType;
import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskListResponse;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.dto.response.TaskStatusResponse;
import com.example.outsourcingproject.domain.task.service.TaskService;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.log.annotation.LogWrite;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.example.outsourcingproject.domain.task.dto.request.TaskReadRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 태스크 생성
    @LogWrite(type = LogType.TASK_CREATED)
    @PostMapping
    public ApiResponse<TaskResponse> createTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskResponse taskResponse = taskService.createTask(taskRequest, userId);

        return ApiResponse.createSuccess("태스크가 성공적으로 생성되었습니다.", taskResponse);
    }

    // 태스크 수정
    @LogWrite(type = LogType.TASK_UPDATED)
    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> updateTask(
            HttpServletRequest request,
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long id
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskResponse taskResponse = taskService.modifyTask(taskRequest, id, userId);

        return ApiResponse.createSuccess("태스크가 성공적으로 수정되었습니다.", taskResponse);
    }
    // 태스크 상태 변경
    @LogWrite(type = LogType.TASK_STATUS_CHANGED)
    @PatchMapping("/{taskId}/status")
    public ApiResponse<TaskStatusResponse> updateTaskStatus(
            HttpServletRequest request,
            @Valid @RequestBody TaskStatusUpdateRequest statusUpdateRequest,
            @PathVariable Long taskId
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        TaskStatusResponse taskResponse = taskService.modifyTaskStatus(statusUpdateRequest, taskId, userId);

        return ApiResponse.createSuccess("태스크 상태가 성공적으로 수정되었습니다.", taskResponse);
    }

    // 태스크 삭제
    @LogWrite(type = LogType.TASK_DELETED)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long userId = Long.valueOf(request.getAttribute("id").toString());
        taskService.softDelete(id, userId);

        return ApiResponse.createSuccess("삭제 완료", null);
    }

    // 태스크 리스트 조회
    @GetMapping
    public ApiResponse<?> getTaskList(
            @ModelAttribute TaskReadRequest taskReadRequest
            ){
        TaskListResponse taskList = taskService.findTasks(taskReadRequest);
        return ApiResponse.createSuccess("조회 성공", taskList);
    }

    // 태스크 단건 조회
    @GetMapping("/{id}")
    public ApiResponse<?> getTask(@PathVariable @NotNull Long id){
        TaskResponse task = taskService.findTask(id);
        return ApiResponse.createSuccess("조회 성공", task);
    }
}
