package com.example.springsecurityjwtredis.model.dto;

import com.example.springsecurityjwtredis.model.enums.JwtHeaderUtilEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;

    private String acessToken;

    private String refreshToken;

    public static TokenDto of(String acessToken, String refreshToken){
        return TokenDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .acessToken(acessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
