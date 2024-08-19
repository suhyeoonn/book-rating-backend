package com.example.bookrating.service;

import com.example.bookrating.dto.ReviewDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.entity.Review;
import com.example.bookrating.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookService bookService;

    public ReviewListResponseDto getReviews(int bookId) {
        bookService.findBookOrThrow(bookId);
        List<ReviewDto> reviewDtos = reviewRepository.findReviewByBookId(bookId).stream()
                .map(ReviewService::getReviewDto)
                .collect(Collectors.toList());

        double averageRating = reviewRepository.findAverageRatingByBookId(bookId).orElse(0.0);
        return new ReviewListResponseDto(reviewDtos, averageRating);
    }

    private static ReviewDto getReviewDto(Review review) {
        return new ReviewDto(review.getId(), review.getRating(), review.getReviewText(), review.getUpdatedAt());
    }
}
