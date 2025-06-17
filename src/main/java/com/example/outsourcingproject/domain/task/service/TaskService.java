package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 태스크 생성
    public TaskResponse createTask(TaskRequest taskRequest, Long userId) {

        LocalDateTime deadline = validateDeadline(taskRequest.getDeadline());

        User foundUser = userRepository
                .findById(userId).orElseThrow(()-> new CustomException(ErrorType.USER_NOT_FOUND));
        User foundManager = userRepository
                .findById(taskRequest.getManagerId())
                .orElseThrow(()-> new CustomException(ErrorType.USER_NOT_FOUND));

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .manager(foundManager)
                .generator(foundUser)
                .priority(taskRequest.getPriority())
                .deadline(deadline)
                .build();

        Task savedTask = taskRepository.save(task);

        return convertToResponse(savedTask);
    }

    // 태스크 수정
    @Transactional
    public TaskResponse modifyTask(TaskRequest taskRequest, Long taskId, Long userId) {
        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);
        User foundManager = userRepository.findById(taskRequest.getManagerId())
                .orElseThrow(()-> new CustomException(ErrorType.USER_NOT_FOUND));

        validateTaskAccessPermission(foundTask, userId);

        LocalDateTime deadline = validateDeadline(taskRequest.getDeadline());

        foundTask.setTitle(taskRequest.getTitle());
        foundTask.setDescription(taskRequest.getDescription());
        foundTask.setPriority(taskRequest.getPriority());
        foundTask.setManager(foundManager);
        foundTask.setDeadline(deadline);

        return convertToResponse(foundTask);
    }

    // 태스크 상태 수정
    @Transactional
    public TaskResponse modifyTaskStatus(TaskStatusUpdateRequest statusUpdateRequest, Long taskId, Long userId) {

        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);

        validateTaskAccessPermission(foundTask, userId);

        validateStatusTransition(foundTask.getStatus(), statusUpdateRequest.getStatus());

        foundTask.setStatus(statusUpdateRequest.getStatus());
        // IN_PROGRESS 일때 시작시간 저장
        if(statusUpdateRequest.getStatus().equals(Status.IN_PROGRESS))
        {
            foundTask.setStartAt(LocalDateTime.now());
        }

        return convertToResponse(foundTask);
    }

    @Transactional
    public void softDelete(Long taskId, Long userId) {

        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);

        validateTaskAccessPermission(foundTask, userId);

        foundTask.setDeleted(true);
        foundTask.setDeletedAt(LocalDateTime.now());
    }

    public static LocalDateTime validateDeadline(LocalDateTime deadline) {

        LocalDateTime resolvedDeadline = deadline != null
                ? deadline
                : LocalDateTime.now().plusWeeks(1); // 기본값: 현재 시간 + 1주

        if (resolvedDeadline.isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorType.INVALID_DEADLINE);
        }

        return resolvedDeadline;
    }

    // 변경 가능한 status 인지 확인
    public static void validateStatusTransition(Status currentStatus, Status newStatus) {
        boolean isValidTransition = false;

        if (currentStatus == Status.TODO && newStatus == Status.IN_PROGRESS) {
            isValidTransition = true;
        } else if (currentStatus == Status.IN_PROGRESS && newStatus == Status.DONE) {
            isValidTransition = true;
        }

        if (!isValidTransition) {
            throw new CustomException(ErrorType.INVALID_TASK_STATUS_TRANSITION);
        }
    }

    private static TaskResponse convertToResponse(Task task) {
        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .managerName(task.getManager().getName())
                .generatorName(task.getGenerator().getName())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .startAt(task.getStartAt())
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getModifiedAt())
                .build();
    }

    private void validateTaskAccessPermission(Task task, Long userId) {
        if (!isTaskAccessible(task, userId)) {
            throw new CustomException(ErrorType.TASK_ACCESS_DENIED);
        }
    }

    private boolean isTaskAccessible(Task task, Long userId) {
        return (task.getGenerator().getId() == userId)
                || (task.getManager().getId() == userId);
    }
}
