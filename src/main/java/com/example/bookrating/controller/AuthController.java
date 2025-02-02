package com.example.bookrating.controller;


import com.example.bookrating.dto.LoginResponseDto;
import com.example.bookrating.dto.MemberDto;
import com.example.bookrating.service.AuthService;
import com.example.bookrating.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;


    /**
     * 회원가입
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDto registerDto) {
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto, HttpSession session) {
        // SecurityContextHolder (Spring Security의 세션)
        // └─ SecurityContext (인증 정보 저장소)
        //     └─ Authentication (인증된 사용자 정보)
        //         ├─ Principal (UserDetails 객체)
        //         ├─ Credentials (비밀번호)
        //         ├─ Authorities (권한 정보)
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberDto.getUsername(), memberDto.getPassword())
            );

            // 인증 정보를 SecurityContext에 저장
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // 세션에 SecurityContext 저장
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            LoginResponseDto loginResponseDto = new LoginResponseDto(new LoginResponseDto.User(Long.parseLong(userDetails.getUsername()), memberDto.getUsername()));

            return ResponseEntity.ok(loginResponseDto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}