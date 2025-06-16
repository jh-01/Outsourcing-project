package com.example.outsourcingproject.domain.log.dto;

import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LogResponse {

    private final int id;
    private final int userId;
    private final int targetId;
    private final LogType type;
    private final String method;

    private final String url;
    private final String ipAddress;
    private final LocalDateTime createAt;


    public static LogResponse toDto(Log log) {
        return new LogResponse(log.getId(),log.getUserId(),log.getTargetId() ,log.getType(), log.getMethod(), log.getUrl(),log.getIpAddress(), log.getCreatedAt());
    }
}
