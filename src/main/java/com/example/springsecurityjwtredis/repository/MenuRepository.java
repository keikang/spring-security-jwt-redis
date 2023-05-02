package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE m.menuUrl LIKE :urlAddr%")
    Optional<Menu> findByMenuUrlLikeQuery(String urlAddr);

    Optional<Menu> findByMenuUrlLike(String urlAddr);

    Optional<Menu> findByMenuUrlStartsWith(String urlAddr);

}
