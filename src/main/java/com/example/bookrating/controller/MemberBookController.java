package com.example.bookrating.controller;

import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.dto.GetMyBooksDto;
import com.example.bookrating.service.MemberBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-books")
@RequiredArgsConstructor
public class MemberBookController {

    private final MemberBookService memberBookService;

    @GetMapping
    public List<GetMyBooksDto> getMyBooks(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return memberBookService.findAll(memberId);
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
