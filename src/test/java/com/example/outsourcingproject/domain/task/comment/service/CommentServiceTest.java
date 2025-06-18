package com.example.outsourcingproject.domain.task.comment.service;

import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.CommentRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.task.service.CommentService;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    TaskRepository taskRepository;


    @Test
    public void addCommentTest() {

        // given
        User user = new User();
        user.setName("이름");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("제목");
        task.setDescription("내용");
        task.setManager(null);
        task.setGenerator(null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class)))
                .thenAnswer(invocation ->
                {Comment comment = invocation.getArgument(0);
                    comment.setUser(user);
                    comment.setContents("댓글 내용");
                    return comment; });

        // when
        ApiResponse<?> saved = commentService.addComment(1L, "댓글 내용");

        // then
        CommentResponseData data = (CommentResponseData) saved.getData();

        assertThat(data.getContents()).isEqualTo("댓글 내용");
        assertThat(data.getTask_id()).isEqualTo(1);
        assertThat(data.getUserName()).isEqualTo("이름");

    }


    @Test
    public void updateCommentTest() {

        // given
        Comment comment = new Comment();
        comment.setContents("수정전 내용");
        comment.setUser(new User());

        when(commentRepository.findByIdAndTaskId(1L, 1L)).thenReturn(Optional.of(comment));

        // when
        ApiResponse<?> updated = commentService.updateComment(1L, 1L, "수정후 내용");

        //then
        CommentResponseData data = (CommentResponseData) updated.getData();

        assertThat(data.getContents()).isEqualTo("수정후 내용");

    }
}
