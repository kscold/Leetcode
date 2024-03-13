package com.example.mallapi.repository;

import com.example.mallapi.domain.Product;
import com.example.mallapi.repository.search.ProductSearch;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Objects;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    // 밑의 리파지터리의 메서드를 실행하면 @Query 어노테이션으로 인해 JSQL 쿼리가 실행
    Optional<Product> selectOne(Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :delFlag where p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("delFlag") boolean flag);

    @Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
