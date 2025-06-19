package com.example.outsourcingproject.domain.task.repository;

import com.example.outsourcingproject.domain.task.entity.Comment;
import com.example.outsourcingproject.domain.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 조회
    Optional<Comment> findByIdAndTaskIdAndIsDeletedFalse(Long taskId, Long commentId);

    // 댓글 페이지에 담아서 조회
    Page<Comment> findByTaskIdAndIsDeletedFalse(Long taskId, Pageable pageable);

    // keyword 포함된 댓글 조회
    Page<Comment> findByTaskIdAndContentContainingAndIsDeletedFalse(Long taskId,String keyword ,Pageable pageable);
}
