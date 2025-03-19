package com.example.bookrating.controller;

import com.example.bookrating.config.PrincipleDetails;
import com.example.bookrating.dto.*;
import com.example.bookrating.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{id}/review")
    public ReviewDto getReview(@PathVariable("id") Long myBookId) {
        return reviewService.findReview(myBookId);
    }

    @PostMapping("/{id}/review")
    public Map<String, Long> addReview(@PathVariable("id") Long id, @RequestBody CreateReviewDto dto, @AuthenticationPrincipal PrincipleDetails principalDetails) {
        Long memberId = principalDetails.getId();
        return reviewService.createReview(id, dto, memberId);
    }

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
