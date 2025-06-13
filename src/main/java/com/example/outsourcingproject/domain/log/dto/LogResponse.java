package com.example.outsourcingproject.domain.log.dto;

import com.example.outsourcingproject.domain.log.entity.Log;
import com.example.outsourcingproject.domain.log.entity.LogType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LogResponse {

    private final int id;
    private final int userId;
    private final LogType type;
    private final String method;

    private final String url;
    private final String ipAddress;
    private final LocalDate localDate;

    public LogResponse(int id, int userId, LogType type, String method, String url, String ipAddress, LocalDate localDate) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.method = method;
        this.url = url;
        this.ipAddress = ipAddress;
        this.localDate = localDate;
    }

    public static LogResponse toDto(Log log) {
        return new LogResponse(log.getId(),log.getUserId(),log.getType(), log.getMethod(), log.getUrl(),log.getIpAddress(), log.getCreatedAt());
    }
}
