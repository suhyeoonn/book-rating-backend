package com.example.bookrating.controller;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.service.BookService;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<BookDto> getBooks(@RequestParam(name = "title", required = false) String title) {
        // title이 제공되지 않으면 전체 목록 반환
        if (title == null || title.isBlank()) {
            return bookService.getBooks();
        }

        // title이 제공된 경우 검색 수행
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("{id}")
    public BookDto getBook(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping("{isbn}/reviews")
    public ReviewListResponseDto getReviews(@PathVariable("isbn") String isbn) {
        return reviewService.getReviews(isbn);
    }
}
