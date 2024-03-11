package com.example.mallapi.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private String fileName;

    @Setter
    private int ord; // 순번

    /*public void setOrd(int ord) {
        this.ord = ord;
    }*/

}
