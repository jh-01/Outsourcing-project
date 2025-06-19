package com.example.outsourcingproject.domain.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUserData {

    private Integer userId;
    private String username;
    private String name;
    private String email;

}
