package com.example.mallapi.service.search;

import com.example.mallapi.domain.Todo;
import com.example.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
