package com.example.mallapi.repository;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.service.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch { // JpaRepository와 QueryDSL 세팅을한 TodoSearch 상속

}
