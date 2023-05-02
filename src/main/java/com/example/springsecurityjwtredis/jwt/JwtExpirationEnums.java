package com.example.springsecurityjwtredis.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpirationEnums {
    //TODO accessToken 기한이 7주일로 돼 있음, 개발 끝나면 수정
    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 30분", 1000L * 60 * 60 * 24 * 7),
    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 7일", 1000L * 60 * 60 * 24 * 7),
    REISSUE_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 3일", 1000L * 60 * 60 * 24 * 3);

    private String description;
    private Long timeValue;
}
