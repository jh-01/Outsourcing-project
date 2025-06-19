//package com.example.outsourcingproject.domain.task.comment.repository;
//
//import com.example.outsourcingproject.domain.task.entity.Comment;
//import com.example.outsourcingproject.domain.task.repository.CommentRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class CommentRepositoryTest {
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Test
//    public void CommentSaveTest() {
//
//        // given
//        Comment comment = new Comment("댓글 내용");
//
//        // when
//        Comment savedComment = commentRepository.save(comment);
//
//        // then
//        assertThat(comment.getContents().equals(savedComment.getContents()));
//
//    }
//
//}
