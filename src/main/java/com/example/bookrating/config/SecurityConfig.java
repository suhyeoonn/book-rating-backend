package com.example.bookrating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // BCryptPasswordEncoder를 빈으로 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/books/**", "/auth/register", "/auth/login", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
                )
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session
                        .maximumSessions(1));
        return http.build();
    }
}
