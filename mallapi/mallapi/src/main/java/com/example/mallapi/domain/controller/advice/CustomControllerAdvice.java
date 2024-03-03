package com.example.mallapi.domain.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
// 에러처리를 하기 위해 하나의 클래스에서 컨트롤러 설정을 함
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    // @ExceptionHandler을 NoSuchElementException.class)로 설정해주므로써 파라마터 에러가 들어감
    public ResponseEntity<?> notExist(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", e.getMessage()));
        // 404이면 body값에 msg로 정의된 에러 코드를 반환
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notExist(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", e.getMessage()));
    }
}
