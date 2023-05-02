package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupId(Long groupId);
}
