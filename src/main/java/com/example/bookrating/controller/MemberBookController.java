package com.example.bookrating.controller;

import com.example.bookrating.config.PrincipleDetails;
import com.example.bookrating.dto.*;
import com.example.bookrating.service.MemberBookService;
import com.example.bookrating.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/my-books")
@RequiredArgsConstructor
public class MemberBookController {

    private final MemberBookService memberBookService;
    private final ReviewService reviewService;

    @GetMapping
    public List<GetMyBooksDto> getMyBooks(@AuthenticationPrincipal PrincipleDetails principalDetails) {
        Long memberId = principalDetails.getId();
        return memberBookService.findAll(memberId);
    }

    @GetMapping("/{id}")
    public GetMyBookDto getMyBook(@PathVariable("id") Long myBookId) {
        return memberBookService.find(myBookId);
    }

    @GetMapping("/exists")
    public BookStatusResponse getBooks(@RequestParam(name = "isbn", required = false) String isbn,
                                       @AuthenticationPrincipal PrincipleDetails principalDetails) {
        // TODO: 왜 bookId가 아니라 isbn을 전달할까? /{bookId}/status
        Long memberId = principalDetails.getId();
        BookStatusResponse response = memberBookService.getBookStatus(isbn, memberId);

        return response;
    }

    /**
     * 책 등록 API
     */
    @PostMapping
    public Map<String, Long> createBook(
            @Valid @RequestBody CreateMemberBookDto createMemberBookDto,
            @AuthenticationPrincipal PrincipleDetails principalDetails) {

        Long memberId = principalDetails.getId();
        return memberBookService.create(createMemberBookDto, memberId);
    }


    @PatchMapping("/{id}/memo")
    public ResponseEntity<String> updateMemo(
            @PathVariable("id") Long id,
            @RequestBody UpdateMemoRequestDto requestDto) {

        memberBookService.updateMemo(id, requestDto.getMemo());
        return ResponseEntity.ok("Memo updated successfully!");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateMemo(
            @PathVariable("id") Long id,
            @RequestBody UpdateStatusRequestDto requestDto) {

        memberBookService.updateStatus(id, requestDto.getStatus());
        return ResponseEntity.ok("Status updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMemberBook(@PathVariable("id") Long id) {
        memberBookService.deleteMemberBook(id);
        return ResponseEntity.ok("MemberBook deleted successfully!");
    }
}
