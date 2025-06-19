package com.example.outsourcingproject.domain.task.comment.service;

import com.example.outsourcingproject.domain.task.dto.CommentResponseData;
import com.example.outsourcingproject.domain.task.dto.CommentResponseDto;
import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import com.example.outsourcingproject.domain.task.repository.CommentRepository;
import com.example.outsourcingproject.domain.task.repository.TaskRepository;
import com.example.outsourcingproject.domain.task.service.CommentService;
import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.domain.user.repository.UserRepository;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.CustomException;
import com.example.outsourcingproject.global.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
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

    @Mock
    UserRepository userRepository;


    @Test
    public void addCommentTest() {

        // given - User, Task, repository 행위, HttpservletRequest
        User user = new User();
        user.setName("이름");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("제목");
        task.setDescription("내용");
        task.setManager(null);
        task.setGenerator(null);

        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setAttribute("id", 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class)))
                .thenAnswer(invocation ->
                {Comment comment = invocation.getArgument(0);
                    comment.setUser(user);
                    comment.setContents("댓글 내용");
                    return comment; });

        // when
        ApiResponse<?> saved = commentService.addComment(1L, "댓글 내용", servletRequest);

        // then - 댓글 내용 검증으로 확인
        CommentResponseData data = (CommentResponseData) saved.getData();

        assertThat(data.getContents()).isEqualTo("댓글 내용");
        assertThat(data.getTask_id()).isEqualTo(1);
        assertThat(data.getUserName()).isEqualTo("이름");

    }


    @Test
    public void updateCommentTest() {

        // given - User, Comment, repository 행위
        Comment comment = new Comment();
        comment.setContents("수정전 내용");
        comment.setUser(new User());

        when(commentRepository.findByIdAndTaskId(1L, 1L)).thenReturn(Optional.of(comment));

        // when
        ApiResponse<?> updated = commentService.updateComment(1L, 1L, "수정후 내용");

        //then - 댓글 내용 검증으로 확인
        CommentResponseData data = (CommentResponseData) updated.getData();

        assertThat(data.getContents()).isEqualTo("수정후 내용");

    }


    @Test
    public void getCommentTest() {

        // given - Pageable, User, Task, List<Comment>, Page<Comment>, repository 행위
        Pageable pageable = PageRequest.of(0,10);

        User user = new User();
        Task task = new Task();

        List<Comment> commentList = List.of(
                new Comment("테스트 댓글1"),
                new Comment( "테스트 댓글2"));
        commentList.get(0).setUser(user);
        commentList.get(1).setUser(user);
        commentList.get(0).setTask(task);
        commentList.get(1).setTask(task);

        Page<Comment> testPage1 = new PageImpl<>(commentList, pageable, commentList.size());

        when(commentRepository.findByTaskId(1L, pageable)).thenReturn(testPage1);

        // when
        ApiResponse<?> gotten = commentService.getCommentList(1L, pageable,null);

        // then - 리스트의 내용을 검증하여 확인
        List<CommentResponseData> data = (List<CommentResponseData>) gotten.getData();

        assertThat(data.get(0).getContents()).isEqualTo("테스트 댓글1");
        assertThat(data.get(1).getContents()).isEqualTo("테스트 댓글2");

    }


    @Test
    public void getCommentTestIncludedKeyword() {

        // given - Pageable, User, Task, List<Comment>, Page<Comment>, repository 행위
        Pageable pageable = PageRequest.of(0,10);

        User user = new User();
        Task task = new Task();

        List<Comment> commentList = List.of(
                new Comment("테스트 댓글1"),
                new Comment( "테스트 댓글2"));
        commentList.get(0).setUser(user);
        commentList.get(1).setUser(user);
        commentList.get(0).setTask(task);
        commentList.get(1).setTask(task);

        List<Comment> filteredList = commentList.stream()
                .filter(comment -> comment.getContents().contains("댓글2"))
                .toList();

        Page<Comment> testPage = new PageImpl<>(filteredList, pageable, commentList.size());

        when(commentRepository.findByTaskIdAndContentsContaining(1L, "댓글2", pageable))
                .thenReturn(testPage);

        // when
        ApiResponse<?> gotten = commentService.getCommentList(1L, pageable,"댓글2");

        // then - 리스트의 내용을 검증하여 확인
        List<CommentResponseData> data = (List<CommentResponseData>) gotten.getData();

        assertThat(data.get(0).getContents()).contains("댓글2");

    }


    @Test
    public void deleteCommentTest() {

        // given - User, Task, Comment, repository 행위
        User user = new User();
        Task task = new Task();
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setDeleted(false);

        when(commentRepository.findByIdAndTaskId(1L, 1L))
                .thenReturn(Optional.of(comment));

        // when
        ApiResponse<?> deleted = commentService.softDeleteComment(1L, 1L);

        // then - Comment 의 isdeleted 필드, 반환 data 가 null 인지 검증하여 확인
        CommentResponseData data = (CommentResponseData) deleted.getData();

        assertThat(data).isEqualTo(null);
        assertThat(comment.isDeleted()).isEqualTo(true);

    }

}
