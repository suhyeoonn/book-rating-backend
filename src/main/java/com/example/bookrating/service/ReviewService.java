package com.example.bookrating.service;

import com.example.bookrating.dto.ReviewDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.dto.ReviewResponseDto;
import com.example.bookrating.entity.Book;
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

        double averageRating = getAverageRating(bookId);
        return new ReviewListResponseDto(reviewDtos, averageRating);
    }

    private double getAverageRating(int bookId) {
        return reviewRepository.findAverageRatingByBookId(bookId).orElse(0.0);
    }

    private static ReviewDto getReviewDto(Review review) {
        return new ReviewDto(review.getId(), review.getRating(), review.getReviewText(), review.getUpdatedAt());
    }

    public ReviewResponseDto addReview(int bookId, ReviewDto dto) {
        Book book = bookService.findBookOrThrow(bookId);
        Review saved = reviewRepository.save(new Review(book, dto.getRating(), dto.getReviewText()));
        return new ReviewResponseDto(getReviewDto(saved), getAverageRating(bookId));
    }
}
