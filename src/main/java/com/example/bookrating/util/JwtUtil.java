package com.example.bookrating.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String generateToken(Long memberId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
        }
        return false;
    }

    // 토큰에서 username(사용자 ID) 추출
    public String getUsername(String token) {
        return parseToken(token).getSubject(); // 🔹 `sub` 필드에서 사용자 ID 추출
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 🔹 토큰 파싱
                .getBody(); // 🔹 Claims(클레임) 반환
    }


}