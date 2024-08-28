package com.example.bookrating.controller;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.BookListDto;
import com.example.bookrating.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<BookListDto> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody BookDto dto) {
        try {
            BookDto savedBook = bookService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (IllegalStateException e) {
            // 중복된 책일 경우 409 Conflict 상태 코드와 함께 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id,  @RequestBody BookDto dto) {
        try {
            BookDto savedBook = bookService.update(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(savedBook);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        try {
            // TODO 책과 연관된 리뷰 먼저 지우기
            bookService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

}
