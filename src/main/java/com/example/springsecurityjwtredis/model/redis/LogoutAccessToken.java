package com.example.springsecurityjwtredis.model.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("logoutAccessToken")
@AllArgsConstructor
@Builder
public class LogoutAccessToken {

    @Id
    private String id;

    private String memberId;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long expiration;

    public static LogoutAccessToken of(String accessToken, String memberId, long expiration){
        return LogoutAccessToken.builder()
                .id(accessToken)
                .memberId(memberId)
                .expiration(expiration)
                .build();
    }
}
