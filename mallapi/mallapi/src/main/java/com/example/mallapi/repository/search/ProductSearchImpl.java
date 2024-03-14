package com.example.mallapi.repository.search;

import com.example.mallapi.domain.Product;

import com.example.mallapi.domain.QProduct;
import com.example.mallapi.domain.QProductImage;
import com.example.mallapi.dto.PageRequestDTO;
import com.example.mallapi.dto.PageResponseDTO;
import com.example.mallapi.dto.ProductDTO;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> seacrhList(PageRequestDTO pageRequestDTO) {

        log.info("----------------searchList-------------------");
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 현재 페이지에서 -1
                pageRequestDTO.getSize(), // 총 페이지 가져옴
                Sort.by("pno").descending()); // pno 기준으로 내림차순 정렬

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product); // Q클래스로 부터 JPQL 쿼리를 실행시킬 수 있는 데이터를 가져옴
        query.leftJoin(product.imageList, productImage); // @ElementCollection을 사용할 때 쿼리DSL 이용 방법

        query.where(productImage.ord.eq(0)); // ord가 0이면

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query); // pageable에 query를 사용해서 페이징을 적용

        List<Tuple> productList = query.select(product, productImage).fetch(); // 쿼리 실행 데이터를 반환

        long count = query.fetchCount();
        log.info("============================================");
        log.info(productList);

        return null;
    }
}
