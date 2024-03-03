package com.example.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder // 생성자 자동 만들기
@AllArgsConstructor // @Builder를 사용하기 위한 설정
@NoArgsConstructor // @Builder를 사용하기 위한 설정

@Table(name = "tbl_todo") // 만들어지는 테이블 이름을 명시
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    @Column(length = 500, nullable = false) // 필드마다 설정을 하기 위해
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
