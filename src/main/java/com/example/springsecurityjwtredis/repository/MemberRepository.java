package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    //Optional<Member> findByMemberName(String memberId);

    @Query("SELECT m FROM Member m JOIN FETCH m.authorities a WHERE m.memberId = :memberId ")
    Optional<Member> findByMemberIdWithAuthorities(String memberId);
}
