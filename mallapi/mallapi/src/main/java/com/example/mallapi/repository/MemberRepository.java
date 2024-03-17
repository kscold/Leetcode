package com.example.mallapi.repository;


import com.example.mallapi.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"memberRoleList"}) // fetch join을 함
    @Query("select m from Member m where m.email = :email") // email 값이 들어오면 그 email에 관련된 데이터만 조회
    Member getWithRoles(@Param("email") String email);

}
