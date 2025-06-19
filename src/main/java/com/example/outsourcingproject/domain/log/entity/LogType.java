package com.example.outsourcingproject.domain.log.entity;

import lombok.Getter;

@Getter
public enum LogType {
    ALL(IdSource.NULL,IdSource.NULL,""),
    // NOTE : userId 요청자 ID
    // NOTE : targetId 대상 ID ( 테스크, 댓글, 로그인, 로그아웃 )
     /*
     userId = { token }
     targetId = { response: { generatorId } }
      */
    TASK_CREATED(IdSource.TOKEN,IdSource.RESPONSE,""),

    /*
     userId = { token }
     targetId = { request: { id } }
     */
    TASK_UPDATED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"id"),

    /*
     userId = { token }
     targetId = { request: { id } }
     */
    TASK_DELETED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"id"),

    /*
     userId = { token }
     targetId = { request: { id } }
     */
    TASK_STATUS_CHANGED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"taskId"),

    /*
     userId = { token }
     targetId = { request: { task_id} }
     */
    COMMENT_CREATED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"task_id"),

    /*
     userId = { token }
     targetId = { request : { id } }
     */
    COMMENT_UPDATED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"id"),

    /*
     userId = { token }
     targetId = { request : { id } }
     */
    COMMENT_DELETED(IdSource.TOKEN,IdSource.PATH_VARIABLE,"id"),

    /*
     userId = { token }
     targetId = { token }
     */
    USER_LOGGED_IN(IdSource.NULL,IdSource.TOKEN,""),
    /*
     userId = { token }
     targetId = { token }
     */
    USER_LOGGED_OUT(IdSource.TOKEN,IdSource.NULL,"");

    private final IdSource userSource;
    private final IdSource targetSource;
    private final String targetKey;

    LogType(IdSource userSource, IdSource targetSource,String targetKey) {
        this.userSource = userSource;
        this.targetSource = targetSource;
        this.targetKey = targetKey;
    }

}
