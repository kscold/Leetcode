package com.example.mallapi.repository.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter { // 모든 Request에 대해서 동작하는 OncePerRequestFilter를 상속

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // true == not checking

        String path = request.getRequestURI();

        log.info("check uri---------------" + path);

        // false == check
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("----------------------------");

        log.info("----------------------------");

        log.info("----------------------------");

        // dest 다음 목적지로 가게 만들어 주는 것
        filterChain.doFilter(request, response);

    }
}
