package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.Entity.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {

    @Query("SELECT u FROM UrlInfo u WHERE u.urlAddr LIKE :urlAddr%")
    Optional<UrlInfo> findByUrlAddrLikeQuery(String urlAddr);

    Optional<UrlInfo> findByUrlAddrLike(String urlAddr);

    Optional<UrlInfo> findByUrlAddrStartsWith(String urlAddr);

}
