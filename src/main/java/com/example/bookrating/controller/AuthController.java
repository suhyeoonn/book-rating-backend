package com.example.bookrating.controller;


import com.example.bookrating.dto.MemberDto;
import com.example.bookrating.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 계정 생성 (회원가입)
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDto registerDto) {
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberDto memberDto, HttpSession session) {
        boolean isAuthenticated = authService.validateLogin(memberDto.getUsername(), memberDto.getPassword());

        if (isAuthenticated) {
            session.setAttribute("username", memberDto.getUsername());
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }
}