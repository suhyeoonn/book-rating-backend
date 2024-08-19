package com.example.bookrating.controller;

import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{bookId}/reviews")
    public ReviewListResponseDto getReviews(@PathVariable("bookId") Integer bookId) {
        return reviewService.getReviews(bookId);
    }
}
