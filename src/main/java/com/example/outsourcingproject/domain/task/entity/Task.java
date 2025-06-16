package com.example.outsourcingproject.domain.task.entity;

import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task")
@Setter
@Getter
@Builder
@AllArgsConstructor
public class Task extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fixme : dev 머지 후 User 엔티티로 연관관계 매핑 필요
    // @ManyToOne
    // @JoinColumn(name = "manager_id")
    // private User manager;
    @NotNull
    private String managerName;

    // @ManyToOne
    // @JoinColumn(name = "generator_id")
    // private User generator;
    @NotNull
    private String generatorName;

    @NotNull
    private String title;
    private String description;

    @NotNull
    private Priority priority;

    private LocalDateTime deadline;

    @Builder.Default
    private Status status = Status.TODO;
    // Status가 IN_PROGRESS 로 변경됐을때의 날짜
    private LocalDateTime startAt;

    @Builder.Default
    private boolean isDeleted = false;
    // 삭제됐을때 시간
    private LocalDateTime deletedAt;

    public Task() {
    }
}
