package com.example.springusermybatis.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey; // 동적으로 생성된 키
    private final long validityInMilliseconds = 3600000; // 1시간 (예제 값)

    // 키 생성자
    public JwtTokenProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HS256에 적합한 키 생성
    }

    // JWT 생성
    public String createToken(long id) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(id)); // 이메일 정보 저장
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 동적 키 사용
                .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 ID 추출
    public long getId(String token) {
        String id = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(id); // String을 long으로 변환
    }
    // 키를 Base64로 인코딩하여 반환 (디버깅용)
//    public String getEncodedKey() {
//        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
//    }
}
