package com.example.bookrating.config;

import com.example.bookrating.entity.Member;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import com.example.bookrating.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // 유저 정보 조회

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String requestURI = request.getRequestURI();

        // ✅ "/books/**" 경로는 검증을 건너뜀
        if (requestURI.startsWith("/books/**")) { // TODO:
            chain.doFilter(request, response); // 🔹 다음 필터로 넘기고 JWT 검증 안 함
            return;
        }

        // 1️⃣ 헤더에서 JWT 토큰 가져오기
        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) { // 🔹 JWT가 유효하면
            String username = jwtUtil.getUsername(token);
            PrincipleDetails userDetails = (PrincipleDetails) userDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // ✅ 인증 정보 저장
        }

        // 다음 필터로 넘김
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}