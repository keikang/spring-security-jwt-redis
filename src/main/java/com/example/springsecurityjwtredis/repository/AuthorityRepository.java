package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    void deleteByMemberMemberIdAndAndRole(String memberId, String role);

    List<Authority>  findByMember_MemberId(String memberId);
}
