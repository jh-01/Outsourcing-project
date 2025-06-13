package com.example.outsourcingproject.domain.tasks.entity;

import com.example.outsourcingproject.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String contents;

    // Entity 병합후 아래 코드 주석해제 필요
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    // Entity 병합후 아래 코드 주석해제 필요
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "feed_id")
//    private Task task;

    public Comment(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}
