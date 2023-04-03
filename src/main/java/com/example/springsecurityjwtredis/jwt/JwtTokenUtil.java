package com.example.springsecurityjwtredis.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
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

    private Key getkey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessToken(String memberId){
        return generateToken(memberId, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME.getTimeValue());
    }

    public String generateRefreshToken(String memberId){
        return generateToken(memberId, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getTimeValue());
    }

    private String generateToken(String memberId, long expiretime) {
        Claims claims = Jwts.claims();
        claims.put("username", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date((System.currentTimeMillis() + expiretime)))
                .signWith(getkey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String userName = getUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public long getRemainMilliSeconds(String token){
        LocalDateTime expiration = getClaims(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        return expiration.getNano() - now.getNano();
    }
}
