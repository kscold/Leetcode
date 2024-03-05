package com.example.mallapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data // 주요롬복 기능을을 묶은 어노테이션
@AllArgsConstructor
@NoArgsConstructor
//@Getter
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;
}
