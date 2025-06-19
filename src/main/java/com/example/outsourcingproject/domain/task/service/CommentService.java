package com.example.outsourcingproject.domain.task.service;

import com.example.outsourcingproject.domain.task.dto.response.CommentData;
import com.example.outsourcingproject.domain.task.dto.response.CommentUserData;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.CommentRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;



    // 댓글 생성 로직
    public ApiResponse<CommentData> addComment(Long taskId, String contents, HttpServletRequest servletRequest) {

        Comment comment = new Comment(contents);

        // taskId 로 task 조회
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new CustomException(ErrorType.TASK_NOT_FOUND));

        // 토큰에서 user_id 가져오기
        Integer userId = (Integer) servletRequest.getAttribute("id");

        // userId 로 user 조회
        User user = userRepository.findById((long)userId).orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        // 댓글 저장
        comment.setTask(task);
        comment.setUser(user);
        commentRepository.save(comment);


        // data 객체 생성
        CommentUserData userData = new CommentUserData(
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getUser().getName(),
                comment.getUser().getEmail()
        );

        CommentData data = new CommentData(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getTask().getGenerator().getId(),
                userData,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );

        return ApiResponse.createSuccess("댓글 생성 성공!", data);

    }


    // 댓글 수정 로직
    @Transactional
    public ApiResponse<CommentData> updateComment(Long taskId, Long commentId, String contents, HttpServletRequest servletRequest) {

        System.out.println(" 댓글 수정 api 의 taskRepository: " + taskRepository);
        System.out.println("댓글 수정 api 의 commentRepository : " + commentRepository);

        // 수정할 댓글 가져오기
        Comment comment = commentRepository.findByIdAndTaskIdAndIsDeletedFalse(commentId, taskId).
                orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        // 댓글 수정
        comment.setContent(contents);

        // 수정된 댓글 저장
        commentRepository.save(comment);

        // data 객체 생성
        CommentUserData userData = new CommentUserData(
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getUser().getName(),
                comment.getUser().getEmail()
        );

        CommentData data = new CommentData(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getTask().getGenerator().getId(),
                userData,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );

        return ApiResponse.createSuccess("수정 성공!", data);


    }


    // 댓글 조회 로직
    public ApiResponse<Page<CommentData>> getCommentList(Long taskId, Pageable pageable, String keyword, HttpServletRequest servletRequest) {

        // 댓글 리스트 조회
        Page<Comment> comments;

        if(!(keyword == null) && !keyword.isBlank()) {
            comments = commentRepository.findByTaskIdAndContentContainingAndIsDeletedFalse(taskId, keyword, pageable);
        } else {
            comments = commentRepository.findByTaskIdAndIsDeletedFalse(taskId, pageable);
        }

        if(comments == null) {
            throw new CustomException(ErrorType.COMMENT_NOT_FOUND);
        }

        // data 만들기
        List<CommentData> commentDataList = comments.getContent().stream()
                .map((comment) ->
                        new CommentData(
                                comment.getId(),
                                comment.getContent(),
                                comment.getTask().getId(),
                                comment.getTask().getGenerator().getId(),
                                new CommentUserData(
                                        comment.getUser().getId(),
                                        comment.getUser().getUsername(),
                                        comment.getUser().getName(),
                                        comment.getUser().getEmail()
                                ),
                                comment.getCreatedAt(),
                                comment.getUpdatedAt()
                        ))
                .collect(Collectors.toList());

        Page<CommentData> data = new PageImpl<>(commentDataList, pageable, commentDataList.size());

        // 응답객체 반환
        return ApiResponse.createSuccess("조회 성공!", data);

    }


    // 댓글 삭제 로직 - 소프트 삭제
    @Transactional
    public ApiResponse<CommentData> softDeleteComment(Long taskId, Long commentId) {

        // 삭제할 댓글 조회
        Comment comment = commentRepository.findByIdAndTaskIdAndIsDeletedFalse(commentId, taskId).orElseThrow(() -> new CustomException(ErrorType.COMMENT_NOT_FOUND));

        // 소프트 삭제 수행
        comment.setDeleted(true);
        comment.setDeletedAt(LocalDateTime.now());

        // 삭제한 내용 저장
        commentRepository.save(comment);

        // 성공시 반환할 data 에 null 할당
        CommentData data = null;

        return ApiResponse.createSuccess("삭제 성공!", data);

    }

}
