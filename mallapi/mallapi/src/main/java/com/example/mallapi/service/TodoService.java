package com.example.mallapi.service;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.TodoDTO;
import jakarta.transaction.Transactional;

@Transactional // 인터페이스에서 트렌젝션을
public interface TodoService {
    TodoDTO get(Long tno);

    // 자바 8 이상의 default 메서드를 통해 엔터페이스에 로직을 선언
    default TodoDTO entityToDTO(Todo todo) {

        return TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();

    }

    default Todo dtoToEntity(TodoDTO todoDTO) {
        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();
    }
}
