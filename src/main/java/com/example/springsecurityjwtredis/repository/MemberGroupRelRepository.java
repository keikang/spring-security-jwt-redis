package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.MemberGroupRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupRelRepository extends JpaRepository<MemberGroupRel, Long> {

    void deleteByMemberMemberId(String memberId);

    void deleteByGroupGroupId(Long groupId);

    List<MemberGroupRel> findByMember_MemberId(String memberId);
}
