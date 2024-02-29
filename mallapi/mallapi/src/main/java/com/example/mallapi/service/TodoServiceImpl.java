package com.example.mallapi.service;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.TodoDTO;
import com.example.mallapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor // 생성자 주입
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno); // 리파지터리에서 아이디에 맞는 값을 반환

        Todo todo = result.orElseThrow(); // 값이 없으면 에러를 던짐, NoSuchElementException 발생

        return entityToDTO(todo); // 엔티티로 변환
    }

}
