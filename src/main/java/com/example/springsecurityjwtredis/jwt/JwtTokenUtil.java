package com.example.springsecurityjwtredis.jwt;

import com.example.springsecurityjwtredis.member.MemberUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getkey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token){
        return getClaims(token).get("username", String.class);
    }

    public String getMemberId(String token){
        return getClaims(token).get("memberId", String.class);
    }

    private Key getkey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessTokenByMemberId(String memberId){
        return generateToken("memberId", memberId, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME.getTimeValue());
    }

    public String generateRefreshTokenByMemberId(String memberId){
        return generateToken("memberId", memberId, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getTimeValue());
    }

    private String generateToken(String key, String memberId, long expiretime) {
        Claims claims = Jwts.claims();
        claims.put(key, memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date((System.currentTimeMillis() + expiretime)))
                .signWith(getkey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateTokenByMemberId(String token, MemberUserDetails memberUserDetails){
        String memberId = getMemberId(token);
        System.out.println("JwtTokenUtil validateTokenByMemberId memberId : "+memberId);
        System.out.println("JwtTokenUtil validateTokenByMemberId memberUserDetails memberId : "+memberUserDetails.getMemberId());
        return memberId.equals(memberUserDetails.getMemberId()) && !isTokenExpired(token);
    }

    public long getRemainMilliSeconds(String token){
        LocalDateTime expiration = getClaims(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime now = LocalDateTime.now();
        System.out.println("JwtTokenUtil getRemainMilliSeconds expiration : "+expiration);
        System.out.println("JwtTokenUtil getRemainMilliSeconds now : "+now);
        Duration duration = Duration.between(now, expiration);
        System.out.println("JwtTokenUtil getRemainMilliSeconds duration.getSeconds() : "+duration.getSeconds());
        return duration.getSeconds();
    }
}
