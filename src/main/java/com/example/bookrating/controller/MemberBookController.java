package com.example.bookrating.controller;

import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.service.MemberBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-books")
@RequiredArgsConstructor
public class MemberBookController {

    private final MemberBookService memberBookService;

    /**
     * 책 등록 API
     */
    @PostMapping
    public ResponseEntity<?> createBook(
            @Valid @RequestBody CreateMemberBookDto createMemberBookDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(memberBookService.create(createMemberBookDto, memberId));
    }
}
