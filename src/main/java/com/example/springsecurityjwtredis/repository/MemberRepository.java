package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByMemberIdAndAndPassword(String memberId, String password);

    @Query("SELECT m FROM Member m JOIN FETCH m.authorities a WHERE m.memberId = :memberId ")
    Optional<Member> findByMemberIdWithAuthorities(String memberId);

    @Query("SELECT m FROM Member m JOIN FETCH m.authorities a WHERE m.username = :username ")
    Optional<Member> findByUsernameWithAuthorities(String username);
}
