package com.example.bookrating.service;

import com.example.bookrating.dto.MemberDto;
import com.example.bookrating.entity.Member;
import com.example.bookrating.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    /**
     * 회원가입 로직
     */
    public void registerUser(MemberDto memberDto) {
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())  // 실제로는 암호화를 적용해야 함 (예: BCrypt)
                .build();

        memberRepository.save(member);
    }

    public boolean validateLogin(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.isPresent() && password.equals(member.get().getPassword());
    }
}
