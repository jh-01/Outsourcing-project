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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private TaskRepository taskRepository;

    // 댓글 생성 로직
    public CommentResponseDto addComment(Long taskId, String contents) {

        Comment comment = new Comment(contents);

        // taskId 로 task 조회
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Task 가 없습니다."));

        // 댓글 저장
        comment.setTask(task);
        commentRepository.save(comment);

        // data 객체 생성
        CommentResponseData data = new CommentResponseData(taskId, comment.getUser().getName(), comment.getContents());

        return new CommentResponseDto(true, "댓글 생성 성공!", data, comment.getCreatedAt());

    }


    // 댓글 수정 로직
    public CommentResponseDto updateComment(Long taskId, Long commentId, String contents) {

        // 수정할 댓글 가져오기
        Comment comment = commentRepository.findByIdAndTaskId(taskId, commentId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment 가 없습니다."));

        // 댓글 수정
        comment.setContents(contents);

        // 수정된 댓글 저장
        commentRepository.save(comment);

        // data 객체 생성
        CommentResponseData data = new CommentResponseData(taskId, comment.getUser().getName(), comment.getContents());

        return new CommentResponseDto(true, "수정 성공!", data, comment.getModifiedAt());


    }

}
