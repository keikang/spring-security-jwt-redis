package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    void deleteByMemberMemberIdAndRoleRoleId(String memberId, Long roleId);

    void deleteByMemberMemberId(String memberId);

    void deleteByGroupGroupId(Long groupId);

    void deleteByRoleRoleId(Long roleId);

    List<Authority>  findByMember_MemberId(String memberId);

    @Query(value = " SELECT r.role_name" +
                   "        , (select count(*) from tb_authority where role_name = r.role_name) as role_count " +
                   "        , data_access_yn   " +
                   "        , role_description  " +
                   "        from tb_role r "
          ,countQuery = "select count(*) from tb_role"
          ,nativeQuery = true)
    Page<Authority> findPageList(Pageable pageable);
}
