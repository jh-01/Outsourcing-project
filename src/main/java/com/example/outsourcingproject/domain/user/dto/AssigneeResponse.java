package com.example.outsourcingproject.domain.user.dto;

import com.example.outsourcingproject.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class AssigneeResponse {
    private Long id;
    private String username;
    private String name;
    private String email;

    public AssigneeResponse(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }

    // User 엔티티에서 UserResponse로 변환하는 정적 메서드
    public static AssigneeResponse from(User user) {
        return AssigneeResponse.builder()
                .id((long)user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}