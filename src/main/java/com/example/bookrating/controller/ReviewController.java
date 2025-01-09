package com.example.bookrating.controller;

import com.example.bookrating.dto.*;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

//    @GetMapping("/{bookId}/reviews")
//    public ReviewListResponseDto getReviews(@PathVariable("bookId") Long bookId) {
//        return reviewService.getReviews(bookId);
//    }

    @PatchMapping("/{reviewId}/rating")
    public ResponseEntity<?> updateRating(@PathVariable("reviewId") Long reviewId, @RequestBody UpdateRatingDto dto) {
        reviewService.updateRating(reviewId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{reviewId}/comment")
    public ResponseEntity<?> updateComment(@PathVariable("reviewId") Long reviewId, @RequestBody UpdateCommentDto dto) {
        reviewService.updateComment(reviewId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
