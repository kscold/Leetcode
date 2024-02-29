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
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno); // 리파지터리에서 아이디에 맞는 값을 반환

        Todo todo = result.orElseThrow(); // 값이 없으면 에러를 던짐, NoSuchElementException 발생

        return entityToDTO(todo); // 엔티티로 변환
    }

    @Override
    public Long register(TodoDTO dto) {
        Todo todo = dtoToEntity(dto); // 엔티티로 벼환
        Todo result = todoRepository.save(todo); // 데이터를 저장

        return result.getTno(); // Tno id를 반환
    }

    @Override
    public void modify(TodoDTO dto) {

        Optional<Todo> result = todoRepository.findById(dto.getTno());

        Todo todo = result.orElseThrow();

        // setter들임
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete()); // boolean 값이기 때문에 isComplete()로 생성됨
        todo.changeDueDate(dto.getDueDate());

        todoRepository.save(todo);

    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }


}
