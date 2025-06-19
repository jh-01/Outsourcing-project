package com.example.outsourcingproject.domain.user.dto;

import com.example.outsourcingproject.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class SimpleAssigneeResponse {
    private Long id;
    private String name;

    @QueryProjection
    public SimpleAssigneeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // User 엔티티에서 UserResponse로 변환하는 정적 메서드
    public static SimpleAssigneeResponse from(User user) {
        return SimpleAssigneeResponse.builder()
                .id((long)user.getId())
                .name(user.getName())
                .build();
    }
}
