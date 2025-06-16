package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.request.TaskRequest;
import com.example.outsourcingproject.domain.task.dto.request.TaskStatusUpdateRequest;
import com.example.outsourcingproject.domain.task.dto.response.TaskResponse;
import com.example.outsourcingproject.domain.task.entity.Status;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // 태스크 생성
    public TaskResponse createTask(TaskRequest taskRequest) {

        LocalDateTime deadline = validateDeadline(taskRequest.getDeadline());

        // fixme : generatorId
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .managerName(taskRequest.getManagerName())
                .generatorName("이호준")
                .priority(taskRequest.getPriority())
                .deadline(deadline)
                .build();

        Task savedTask = taskRepository.save(task);

        return convertToResponse(savedTask);
    }

    // 태스크 수정
    // fixme : setManager
    @Transactional
    public TaskResponse modifyTask(TaskRequest taskRequest, Long taskId) {
        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);
        // TODO 인증/인가 : manager , generator만 가능
        LocalDateTime deadline = validateDeadline(taskRequest.getDeadline());

        foundTask.setTitle(taskRequest.getTitle());
        foundTask.setDescription(taskRequest.getDescription());
        foundTask.setPriority(taskRequest.getPriority());
        foundTask.setManagerName(taskRequest.getManagerName());
        foundTask.setDeadline(deadline);

        return convertToResponse(foundTask);
    }

    // 태스크 상태 수정
    @Transactional
    public TaskResponse modifyTaskStatus(TaskStatusUpdateRequest statusUpdateRequest, Long taskId) {
        // TODO 인증/인가 : manager , generator만 가능
        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);

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
    public void softDelete(Long taskId) {
        // TODO 인증/인가 : manager , generator만 가능
        Task task = taskRepository.findByIdOrElseThrow(taskId);

        task.setDeleted(true);
        task.setDeletedAt(LocalDateTime.now());
    }

    public static LocalDateTime validateDeadline(LocalDateTime deadline) {

        LocalDateTime resolvedDeadline = deadline != null
                ? deadline
                : LocalDateTime.now().plusWeeks(1); // 기본값: 현재 시간 + 1주

        if (resolvedDeadline.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("마감시간이 현재시간보다 과거임");
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
            throw new RuntimeException(
                    String.format("'%s'에서 '%s'로 변경할 수 없습니다.",
                            currentStatus.name(), newStatus.name())
            );
        }
    }

    private static TaskResponse convertToResponse(Task task) {
        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                // fixme
                .managerName(task.getManagerName())
                .generatorName(task.getGeneratorName())
                .priority(task.getPriority())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .startAt(task.getStartAt())
                .createdAt(task.getCreatedAt())
                .lastModifiedAt(task.getModifiedAt())
                .build();
    }
}
