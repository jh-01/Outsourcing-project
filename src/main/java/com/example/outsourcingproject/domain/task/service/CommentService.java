package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.CommentRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    // 댓글 생성 로직
    public final CommentResponseDto addComment(Long taskId, String contents) {

        Comment comment = new Comment(contents);

//        System.out.println(" taskRepository: " + taskRepository);

        // taskId 로 task 조회
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new CustomException(ErrorType.TASK_NOT_FOUND));

        // 댓글 저장
        comment.setTask(task);
        commentRepository.save(comment);

        // data 객체 생성
        CommentResponseData data = new CommentResponseData(taskId, comment.getUser().getName(), comment.getContents());

        return new CommentResponseDto(true, "댓글 생성 성공!", data, comment.getCreatedAt());

    }


    // 댓글 수정 로직
    @Transactional
    public CommentResponseDto updateComment(Long taskId, Long commentId, String contents) {

        // 수정할 댓글 가져오기
        Comment comment = commentRepository.findByIdAndTaskId(taskId, commentId).
                orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        // 댓글 수정
        comment.setContents(contents);

        // 수정된 댓글 저장
        commentRepository.save(comment);

        // data 객체 생성
        CommentResponseData data = new CommentResponseData(taskId, comment.getUser().getName(), comment.getContents());

        return new CommentResponseDto(true, "수정 성공!", data, comment.getModifiedAt());


    }


    // 댓글 조회 로직
    public Page<CommentResponseDto> getCommentList(Long taskId, Pageable pageable, String keyword) {

        // 댓글 리스트 조회
        Page<Comment> comments;

        if(!(keyword == null) && !keyword.isBlank()) {
            comments = commentRepository.findByTaskIdAndContentsContaining(taskId, keyword, pageable);
        } else {
            comments = commentRepository.findByTaskId(taskId, pageable);
        }

        // 댓글을 응답객체로 변환
        List<CommentResponseDto> CommentResponseDtos = comments.stream().map(CommentResponseDto::toDto).collect(Collectors.toList());

        // 응답객체를 페이지객체로 변환
        return new PageImpl<>(CommentResponseDtos, pageable, comments.getTotalElements());

    }


    // 댓글 삭제 로직 - 소프트 삭제
    @Transactional
    public CommentResponseDto softDeleteComment(Long taskId, Long commentId) {

        // 삭제할 댓글 조회
        Comment comment = commentRepository.findByIdAndTaskId(taskId, commentId).orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        // 소프트 삭제 수행
        comment.setDeleted(true);
        comment.setDeletedAt(LocalDateTime.now());

        // 삭제한 내용 저장
        commentRepository.save(comment);

        // 성공시 반환할 data 에 null 할당
        CommentResponseData data = null;

        return new CommentResponseDto(true, "삭제 성공!", data, comment.getModifiedAt());

    }

}
