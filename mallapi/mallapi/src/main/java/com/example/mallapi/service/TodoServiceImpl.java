package com.example.mallapi.service;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.PageRequestDTO;
import com.example.mallapi.dto.PageResponseDTO;
import com.example.mallapi.dto.TodoDTO;
import com.example.mallapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // change 메서드들은 사실 setter임
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete()); // boolean 값이기 때문에 isComplete()로 생성됨
        todo.changeDueDate(dto.getDueDate());

        todoRepository.save(todo);

    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {

        // JPA 타입을 Page 객체로 받음
        Page<Todo> result = todoRepository.search1(pageRequestDTO);

        // Todo리스트 타입이 TodoDTO가 되어야함
        List<TodoDTO> dtoList = result
                .get() // 객체들을 가져옴
                .map(todo -> entityToDTO(todo)) // 객체들을 뿌리면서 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // DTO를 리스트로 반듬

        PageResponseDTO<TodoDTO> responseDTO =
                PageResponseDTO.<TodoDTO>withAll()
                        .dtoList(dtoList) // Page 리스트 객체 dto에 대입
                        .pageRequestDTO(pageRequestDTO) // PageRequestDTO 객체를 dto에 대입
                        .total(result.getTotalElements()) // 문자열 1을 포함한 데이터를 찾아주는 쿼리의 갯수를 dto에 대입
                        .build(); // 결과적으로 PageResponseDTO 객체가 완성

        return responseDTO; // PageResponseDTO 객체를 반환
    }
}
