package com.example.bookrating.controller;

import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.service.MemberBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-books")
@RequiredArgsConstructor
public class MemberBookController {

    private final MemberBookService memberBookService;

    @GetMapping
    public ResponseEntity<?> getMyBooks(@AuthenticationPrincipal UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current Authentication: " + auth);
        return ResponseEntity.ok("Welcome " + userDetails.getUsername() + ", here are your books.");
    }
    /**
     * 책 등록 API
     */
    @PostMapping
    public ResponseEntity<?> createBook(
            @Valid @RequestBody CreateMemberBookDto createMemberBookDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        memberBookService.create(createMemberBookDto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
