package com.example.bookrating.service;

import com.example.bookrating.dto.ReviewDto;
import com.example.bookrating.dto.ReviewListResponseDto;
import com.example.bookrating.dto.ReviewResponseDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Review;
import com.example.bookrating.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookService bookService;

    public ReviewListResponseDto getReviews(Long bookId) {
        bookService.findBookOrThrow(bookId);
        List<ReviewDto> reviewDtos = reviewRepository.findReviewByBookId(bookId).stream()
                .map(ReviewService::getReviewDto)
                .collect(Collectors.toList());

        double averageRating = getAverageRating(bookId);
        return new ReviewListResponseDto(reviewDtos, averageRating);
    }

    private double getAverageRating(Long bookId) {
        Double averageRating = reviewRepository.findAverageRatingByBookId(bookId).orElse(0.0);
        return BigDecimal.valueOf(averageRating)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static ReviewDto getReviewDto(Review review) {
        return new ReviewDto(review.getId(), review.getRating(), review.getReviewText(), review.getUpdatedAt());
    }

    public ReviewResponseDto addReview(Long bookId, ReviewDto dto) {
        Book book = bookService.findBookOrThrow(bookId);
        Review saved = reviewRepository.save(new Review(book, dto.getRating(), dto.getReviewText()));
        return new ReviewResponseDto(getReviewDto(saved), getAverageRating(bookId));
    }

    public ReviewResponseDto updateReview(Long bookId, Long reviewId, ReviewDto dto) {
        Book book = bookService.findBookOrThrow(bookId);

        Review review = findReviewOrThrow(reviewId);

        review.patch(new Review(book, dto.getRating(), dto.getReviewText()));

        Review saved = reviewRepository.save(review);
        return new ReviewResponseDto(getReviewDto(saved), getAverageRating(bookId));
    }

    private Review findReviewOrThrow(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalStateException("존재하지 않는 리뷰입니다"));
    }

    public ReviewResponseDto deleteReview(Long bookId, Long reviewId) {
        bookService.findBookOrThrow(bookId);
        findReviewOrThrow(reviewId);
        reviewRepository.deleteById(reviewId);
        return new ReviewResponseDto(null, getAverageRating(bookId));
    }
}
