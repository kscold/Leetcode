package com.example.mallapi.config;

import com.example.mallapi.controller.formatter.LocalDateFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {
    // WebMvcConfigurer 스프링의 많은 기능 설정을 포함하고 있기 때문에 상속


    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("-----------------------");
        log.info("addFormatters"); // 제대로 동작을 하는지 확인하기 위해 로깅을 설정

        registry.addFormatter(new LocalDateFormatter());
    }

    // 스프링 시큐리티를 추가하면서 CORS 설정 부분을 시큐리티쪽에 설정을 함
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS 설정을 하기 위해 메서드 추가
        registry.addMapping("/**") // 모든 패스에서 설정
                .maxAge(500) // 최대 시간 설정
                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTION") // 허용할 메서드 설정, OPTION으로 preflight 허용을 설정함
                .allowedOrigins("*"); // 허락할 리소스를 모두로 설정
    }*/
}
