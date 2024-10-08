package com.example.bookrating.controller;

import com.example.bookrating.dto.ReviewDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.dto.ReviewResponseDto;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{bookId}/reviews")
    public ReviewListResponseDto getReviews(@PathVariable("bookId") Long bookId) {
        return reviewService.getReviews(bookId);
    }

    @PostMapping("/{bookId}/reviews")
    public ReviewResponseDto addReview(@PathVariable("bookId") Long bookId, @RequestBody ReviewDto dto) {
        return reviewService.addReview(bookId, dto);
    }

    @PatchMapping("/{bookId}/reviews/{reviewId}")
    public ReviewResponseDto updateReview(@PathVariable("bookId") Long bookId, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewDto dto) {
        return reviewService.updateReview(bookId, reviewId, dto);
    }

    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    public ReviewResponseDto deleteReview(@PathVariable("bookId") Long bookId, @PathVariable("reviewId") Long reviewId) {
        return reviewService.deleteReview(bookId, reviewId);
    }
}
