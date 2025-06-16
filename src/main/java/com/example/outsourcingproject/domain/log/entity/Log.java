package com.example.outsourcingproject.domain.log.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    private int targetId;

    @Column(nullable = false)
//    @JoinColumn(name="user_id")
    private int userId;

    @Column(nullable = false)
    private LogType type;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String ipAddress;

    @CreatedDate
    private LocalDateTime createdAt;

    public Log(int targetId, int userId, LogType type, String method, String url, String ipAddress) {
        this.targetId = targetId;
        this.userId = userId;
        this.type = type;
        this.method = method;
        this.url = url;
        this.ipAddress = ipAddress;
    }
}
