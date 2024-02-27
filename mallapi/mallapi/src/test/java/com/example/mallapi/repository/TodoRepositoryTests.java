package com.example.mallapi.repository;


import com.example.mallapi.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2 // 원래는 테스트 메서드에 롬복을 쓰는것이 일반적인 것은 아니지만 build.gradle에서 사용할 수 있도록 설정
public class TodoRepositoryTests {

    @Autowired // 주입을 받기 위해 명시
    private TodoRepository todoRepository;

    @Test
    public void test1() {
        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName()); // 리파지터리의 클래스 이름을 로깅
        // 자동적으로 jdk가 프록시를 잡아줌
    }

    @Test
    public void testInsert() {

        for (int i = 0; i < 100; i++) {

            Todo todo = Todo.builder() // 저장할 값을 넣어봄
                    .title("Title" + i)
                    .content("Content..." + i)
                    .dueDate(LocalDate.of(2023, 12, 30))
                    .build();

            Todo result = todoRepository.save(todo);

            log.info(result);
        }
    }

    @Test
    public void testRead() {
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno); // 반환값이 옵셔널이므로 옵셔널 객체로 받아야함

        Todo todo = result.orElseThrow();

        log.info(todo);
    }


    @Test
    public void testUpdate() {
        // 먼저 로딩하고 엔티티 객체를 변경 setter를 쓰게 됨(setter이름을 직관적으로 changeXXX라고 작명함)
        Long tno = 1L;

        Optional<Todo> read = todoRepository.findById(tno);

        Todo todo = read.orElseThrow();

        todo.changeTitle("Update Title");
        todo.changeContent("Update content");
        todo.changeComplete(true);

        Todo result = todoRepository.save(todo);

        log.info(result);
    }

    @Test
    public void testPaging() {
        // 페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending()); // id를 내림차순으로 정렬

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements()); // 선택한 필드하나의 모든 테이터를 보여줌

        log.info(result.getContent()); // 선택한 필드 모든 데이터를 보여줌
    }
}
