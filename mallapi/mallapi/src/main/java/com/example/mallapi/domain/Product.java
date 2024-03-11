package com.example.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList") // 컬렉션이나 연관관계가 들어가는 부분은 ToString을 제외하는 것이 좋음
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDesc(String desc) {
        this.pdesc = desc;
    }

    public void changeName(String name) {
        this.pname = name;
    }

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image) {
        image.setOrd(imageList.size()); // imageList의 갯수만큼 image 번호 설정
        imageList.add(image); // imageList에 image 엔티티 추가
    }

    public void addImageString(String fileName) { // 이미지 파일 이름을 설정하는 메서드
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);// addImage 메서드를 실행
    }

    public void clearList() { // 모든 imageList 데이터를 삭제하는 메서드
        this.imageList.clear();
    }
}

