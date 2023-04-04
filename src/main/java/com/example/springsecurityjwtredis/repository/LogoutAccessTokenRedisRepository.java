package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.redis.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
