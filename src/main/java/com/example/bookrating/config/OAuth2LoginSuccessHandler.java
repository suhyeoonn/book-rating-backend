package com.example.bookrating.config;

import com.example.bookrating.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 사용자 정보 가져오기
        PrincipleDetails userDetails = (PrincipleDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();  // username 가져오기
        Long userId = userDetails.getId();

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(userId, username);

        // 프론트엔드로 리디렉트 (토큰 포함)
        response.sendRedirect(frontendUrl+"/auth/success?token=" + token);
    }
}
