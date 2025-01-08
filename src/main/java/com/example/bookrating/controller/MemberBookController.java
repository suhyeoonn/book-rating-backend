package com.example.bookrating.controller;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.dto.GetMyBookDto;
import com.example.bookrating.dto.GetMyBooksDto;
import com.example.bookrating.entity.MemberBook;
import com.example.bookrating.service.MemberBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public GetMyBookDto getMyBook(@PathVariable("id") Long myBookId) {
        return memberBookService.find(myBookId);
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

    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> getBooks(@RequestParam(name = "isbn", required = false) String isbn,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        boolean exists = memberBookService.exists(isbn, memberId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }
}
