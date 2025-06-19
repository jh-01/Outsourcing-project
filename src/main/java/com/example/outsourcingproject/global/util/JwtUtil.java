package com.example.outsourcingproject.global.util;

import com.example.outsourcingproject.domain.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // JWT 토큰의 접두사
    public static final String BEARER_PREFIX = "Bearer ";
    // JWT 토큰의 만료 시간 (밀리초 단위, 여기서는 60분)
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    // JWT 서명 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 애플리케이션 설정 파일에서 주입받은 비밀 키
    @Value("${jwt.secret.key}")
//    @Value("${jwt.secret.key:SUpZQkM1NjhBTkd2UXhaaVZ2eGpPRnpoQ2w1MkJTVG5EZlJLaU4=}")
    private String secretKey;
    // 실제 서명에 사용되는 키 객체
    private Key key;


    /**
     * 빈 초기화 메서드
     * - 애플리케이션 시작 시 비밀 키를 Base64로 디코딩하여 Key 객체를 초기화합니다.
     */
    @PostConstruct
    public void init() {
        log.info("🔐 Raw secretKey string = {}", secretKey);

        byte[] bytes = Base64.getDecoder().decode(secretKey);
        log.info("🔐 Decoded key byte length = {}", bytes.length);  // ✅ 32 이상이어야 함

        key = Keys.hmacShaKeyFor(bytes);
    }


    /**
     * JWT 토큰을 생성합니다.
     * @param id 사용자 고유 아이디
     * @param username 사용자 아이디
     * @param role 사용자 권한
     * @return 생성된 JWT 토큰
     */
    public String createToken(int id, String username, UserRole role) {
        Date date = new Date();
        String token = Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("username", username)
                .claim("role", role.name())
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

        log.info("✅ Created JWT Token = {}", token);  // 이거 찍어보세요
        return token;
    }

    /**
     * JWT 토큰에서 역할(권한) 정보를 추출합니다.
     * @param token JWT 토큰
     * @return 역할 정보 (문자열)
     */
    public String extractRoles(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public int extractId(String token){
        return Integer.parseInt(extractClaims(token).getSubject());
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    // "Bearer " 접두사가 붙은 토큰 문자열에서 접두사 제거하고 순수 JWT 토큰 반환
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new IllegalArgumentException("Not Found Token");
    }

    // JWT 토큰을 파싱해 클레임(토큰에 담긴 정보) 반환
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
