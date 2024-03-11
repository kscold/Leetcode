package com.example.mallapi.service.search;

import com.example.mallapi.domain.QTodo;
import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.PageRequestDTO;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// qeurydsl을 설정하기 위해 이름에 Impl를 붙임
@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class); // QuerydslRepositorySupport의 this.builder를 설정
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) { // search1 메서드는 문자열 1을 포함한 값을 10개씩 찾아서 반환해주는 쿼리를 실행
        log.info("search1..........................");

        QTodo todo = QTodo.todo; // todo에 관련된 객체
        JPQLQuery<Todo> query = from(todo); // todo엔티티를 가지고 쿼리를 사용하게 됨(JPQL 쿼리로 변환함)

//        query.where(todo.title.contains("1")); // title에 문자열에 1을 포함하는 타이틀을 찾는 쿼리

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());
        // 페이징 설정 0부터 시작하기 때문에 -1을 해줌

        this.getQuerydsl().applyPagination(pageable, query); // 페이징 처리

        List<Todo> list= query.fetch(); // 목록 데이터 query 실행

        long total = query.fetchCount(); // 실행한 쿼리 카운트


        return new PageImpl<>(list, pageable, total); // Querydsl의 공식문서에 나와있음 PageImpl<> 타입을 반환해야함
    }
}
