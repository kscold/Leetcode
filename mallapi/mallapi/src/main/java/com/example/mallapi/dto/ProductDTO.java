package com.example.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long pno; // 가격 id
    private String pname; // 상품 이름
    private int price; // 상품 가격
    private String pdesc; // 상품 설명
    private boolean delFlag; // 상품 삭제를 실제로 하지 않는 소프트 삭제를 하기 때문에 Flag로 설정을 함

    @Builder.Default // 빌더 패턴을 사용할 때 기본 데이터값을 설정하기 위해
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}

