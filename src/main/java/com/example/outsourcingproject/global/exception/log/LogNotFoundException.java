package com.example.outsourcingproject.global.exception.log;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LogNotFoundException extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
  public LogNotFoundException(String message) {
    super(message);
  }
  public LogNotFoundException(){
    super("로그를 찾지 못했습니다.");
  }

}
