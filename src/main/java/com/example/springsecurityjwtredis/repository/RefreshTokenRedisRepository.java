package com.example.springsecurityjwtredis.repository;

import com.example.springsecurityjwtredis.model.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
