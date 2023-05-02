package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Role;
import com.example.springsecurityjwtredis.model.reponse.RoleResConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = " SELECT r.role_id as roleId" +
            "        , r.role_name as roleName" +
            "        , (select count(*) from tb_authority where role_id = r.role_id) as roleCount " +
            "        , data_access_yn as dataAccessYn" +
            "        , role_description as roleDescription  " +
            "        from tb_role r "
            ,countQuery = "select count(*) from tb_role"
            ,nativeQuery = true)
    List<RoleResConverter> rolePageList(Pageable pageable);
    //Page<RoleResConverter> findPageList(Pageable pageable);
    @Query(value = " SELECT r.role_id as roleId" +
            "        , r.role_name as roleName" +
            "        , (select count(*) from tb_authority where role_id = r.role_id) as roleCount " +
            "        , data_access_yn as dataAccessYn" +
            "        , role_description as roleDescription  " +
            "        from tb_role r "
            ,countQuery = "select count(*) from tb_role"
            ,nativeQuery = true)
    List<RoleResConverter> findTotalList();

    Optional<Role> getRoleByRoleId(Long roleId);

    Optional<Role> getRoleByRoleName(String roleName);
}
