package com.example.mallapi.repository.search;

import com.example.mallapi.domain.QTodo;
import com.example.mallapi.domain.Todo;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

// qeurydsl을 설정하기 위해 이름에 Impl를 붙임
@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class); // QuerydslRepositorySupport의 this.builder를 설정
    }

    @Override
    public Page<Todo> search1() { // search1 메서드를 오버라이드
        log.info("search1");

        QTodo todo = QTodo.todo; // todo에 관련된 객체
        JPQLQuery<Todo> query = from(todo); // todo엔티티를 가지고 쿼리를 사용하게 됨

        query.where(todo.title.contains("1")); // title에 1을 포함하는 타이틀을 찾는 쿼리

        Pageable pageable = PageRequest.of(1, 10, Sort.by("tno").descending()); // 페이징 설정
        this.getQuerydsl().applyPagination(pageable, query); // 페이징 처리

        query.fetch(); // 목록 데이터 query 실행

        query.fetchCount();

        return null;
    }
}
