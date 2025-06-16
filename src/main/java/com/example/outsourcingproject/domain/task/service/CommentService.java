package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.CommentRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private TaskRepository taskRepository;

    // 댓글 생성 로직
    public CommentResponseDto addComment(Long id, String contents) {

        Comment comment = new Comment(contents);

        // taskId 로 task 조회
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Task 가 없습니다."));

        // 댓글 저장
        comment.setTask(task);
        commentRepository.save(comment);

        // data 객체 생성
        CommentResponseData data = new CommentResponseData(id, comment.getUser().getName(), comment.getContents());

        return new CommentResponseDto(true, "댓글 생성 성공!", data, comment.getCreatedAt());

    }

}
