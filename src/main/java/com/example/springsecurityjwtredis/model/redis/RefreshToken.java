package com.example.springsecurityjwtredis.model.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String memberId, String refreshToken, Long remainingTiemMs){
        System.out.println("RefreshToken createRefreshToken refreshToken : "+refreshToken);
        return RefreshToken.builder()
                .id(memberId)
                .refreshToken(refreshToken)
                .expiration(remainingTiemMs / 1000)
                .build();
    }

}
