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
    public List<BookListDto> getBooks(@RequestParam(name = "title", required = false) String title) {
        // title이 제공되지 않으면 전체 목록 반환
        if (title == null || title.isBlank()) {
            return bookService.getBooks();
        }

        // title이 제공된 경우 검색 수행
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/books/{id}")
    public BookListDto getBook(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }


}
