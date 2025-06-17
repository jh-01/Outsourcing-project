package com.example.outsourcingproject.domain.task.entity;

import com.example.outsourcingproject.domain.user.entity.User;
import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    @Column()
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Column()
    private LocalDateTime startAt;

    @Column()
    private boolean isDeleted;

    @Column()
    private LocalDateTime deletedAt;

}
