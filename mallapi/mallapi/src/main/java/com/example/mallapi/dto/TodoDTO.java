package com.example.mallapi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder // 객체 생성을 유연하게 만들기 위해 빌더 패턴
@AllArgsConstructor // 생성자 선언
@NoArgsConstructor // 생성자 선언을 위한 에러 방지
public class TodoDTO {
    private Long tno;

    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;
}
