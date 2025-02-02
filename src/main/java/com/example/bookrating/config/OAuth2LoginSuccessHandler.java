package com.example.bookrating.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 사용자 정보 가져오기
        PrincipleDetails userDetails = (PrincipleDetails) authentication.getPrincipal();

        // JSON 응답 생성
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", userDetails.getAttributes());
        responseData.put("message", "OAuth2 로그인 성공");

        String token = "123"; // TODO:

        // next 값이 있으면 해당 페이지로 리디렉트
        String nextUrl = request.getParameter("next");
        String redirectUrl = (nextUrl != null)
                ? "http://localhost:3000" + nextUrl + "?token=" + token
                : "http://localhost:3000/auth/success?token=" + token;

        response.sendRedirect(redirectUrl);
    }
}
