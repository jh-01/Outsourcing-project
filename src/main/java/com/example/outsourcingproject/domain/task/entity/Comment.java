package com.example.outsourcingproject.domain.task.entity;

import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Task task;

    @Column
    private boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    public Comment(String content) {
        this.content = content;
    }
}
