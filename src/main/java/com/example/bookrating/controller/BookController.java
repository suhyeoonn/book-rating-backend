package com.example.bookrating.controller;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.PopularBookDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.service.AladinService;
import com.example.bookrating.service.BookService;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AladinService aladinService;


    @GetMapping
    public List<BookDto> getItemList(
            @RequestParam(name = "categoryId") int categoryId,
            @RequestParam(name = "queryType", defaultValue = "Bestseller") String queryType,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return aladinService.getItemList(categoryId, queryType, size);
    }

    @GetMapping("{id}")
    public BookDto getBook(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping("{isbn}/reviews")
    public ReviewListResponseDto getReviews(@PathVariable("isbn") String isbn) {
        return reviewService.getReviews(isbn);
    }

    @GetMapping("/popular")
    public List<PopularBookDto> getPopularBooks() {
        return bookService.getPopularBooks(1);
    }

    @GetMapping("/most-added")
    public List<PopularBookDto> getMostAddedBooks() {
        return bookService.getMostAddedBooks();
    }
}
