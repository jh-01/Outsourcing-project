package com.example.outsourcingproject.domain.task.entity;

import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task")
@Setter
@Getter
@NoArgsConstructor
public class Task extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generator_id", nullable = false)
    private User generator;

    @Column(nullable = false)
    private String title;

    private String description;

    private Priority priority;

    @Column()
    private LocalDateTime deadline;

    private Status status;

    private LocalDateTime startAt;

    private boolean isDeleted;

    private LocalDateTime deletedAt;
}
