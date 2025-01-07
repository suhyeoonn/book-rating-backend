package com.example.bookrating.service;

import com.example.bookrating.dto.MemberDto;
import com.example.bookrating.entity.Member;
import com.example.bookrating.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 로직
     */
    public void registerUser(MemberDto memberDto) {
        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(encryptedPassword)
                .build();

        memberRepository.save(member);
    }

    public boolean validateLogin(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.isPresent() && passwordEncoder.matches(password, member.get().getPassword());
    }
}
