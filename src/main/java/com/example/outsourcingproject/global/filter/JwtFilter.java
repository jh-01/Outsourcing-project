package com.example.outsourcingproject.global.filter;

import com.example.outsourcingproject.domain.user.entity.UserRole;
import com.example.outsourcingproject.global.common.ApiResponse;
import com.example.outsourcingproject.global.exception.ErrorType;
import com.example.outsourcingproject.global.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        String authorizationHeader = httpRequest.getHeader("Authorization");

        // OPTIONS 요청은 CORS 사전 요청으로 별도의 인증 없이 바로 통과
        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        // 회원가입, 로그인은 필터 건너뛰고 그대로 진행
        if (requestURI.equals("/api/auth/register") || requestURI.equals("/api/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 여부 확인
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("JWT 토큰이 필요 합니다.");
            sendErrorResponse((HttpServletResponse)response, ErrorType.TOKEN_NOT_FOUND);
            return;
        }

        String jwt = jwtUtil.substringToken(authorizationHeader);

        try {
            // JWT 유효성 검사와 claims 추출
            Claims claims = jwtUtil. extractClaims(jwt);
            if (claims == null) {
                sendErrorResponse((HttpServletResponse)response, ErrorType.INVALID_TOKEN);
                return;
            }

            httpRequest.setAttribute("id", Integer.parseInt(claims.getSubject()));
            httpRequest.setAttribute("role", claims.get("role"));

            // 토큰에서 username 추출
            String username = jwtUtil.extractUsername(jwt);

            // 토큰에서 role 추출 후 UserRole enum 으로 변환
            String role = jwtUtil.extractRoles(jwt);
            UserRole userRole = UserRole.valueOf(role);

            // 권한 정보를 SimpleGrantedAuthority 리스트로 생성
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole.getRole()));
            User user = new User(username, "", authorities);

            // SecurityContext에 인증 객체를 저장하여 현재 요청의 인증 상태를 설정
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));


            chain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            sendErrorResponse((HttpServletResponse)response, ErrorType.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            sendErrorResponse((HttpServletResponse)response, ErrorType.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            sendErrorResponse((HttpServletResponse)response, ErrorType.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            log.error("Invalid JWT token, 유효하지 않는 JWT 토큰 입니다.", e);
            sendErrorResponse((HttpServletResponse)response, ErrorType.INVALID_TOKEN);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        response.setStatus(errorType.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.writeValue(response.getWriter(), ApiResponse.createError(errorType));
    }

}
