package com.example.bookrating.service;

import com.example.bookrating.dto.*;
import com.example.bookrating.entity.MemberBook;
import com.example.bookrating.entity.Review;
import com.example.bookrating.repository.MemberBookRepository;
import com.example.bookrating.repository.MemberRepository;
import com.example.bookrating.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberBookRepository memberBookRepository;


    public Map<String, Long> createReview(Long memberBookId, CreateReviewDto requestDto, Long memberId) {
        MemberBook memberBook = memberBookRepository.findById(memberBookId).orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        Review review = new Review(
                memberBook.getBook(),
                requestDto.getRating(),
                requestDto.getComment(),
                memberBook.getMember()
        );
        memberBook.setReview(review);
        memberBookRepository.save(memberBook);

        Map<String, Long> response = new HashMap<>();
        response.put("reviewId", review.getId());
        return response;
    }

    public void updateRating(Long reviewId, UpdateRatingDto dto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        review.patch(new Review(dto.getRating(), null));

        reviewRepository.save(review);
    }

    public void updateComment(Long reviewId, UpdateCommentDto dto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        review.patch(new Review(null, dto.getComment()));

        reviewRepository.save(review);
    }

    public ReviewSummaryDto getReviewSummary(Long bookId) {
        ReviewSummaryDto summary = reviewRepository.findReviewSummaryByBookId(bookId)
                .orElse(new ReviewSummaryDto(0.0, 0L)); // 기본값 설정

        // 소수점 둘째자리까지 반올림 처리
        double roundedAverageRating = BigDecimal.valueOf(summary.getAverageRating())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        summary.setAverageRating(roundedAverageRating);
        return summary;
    }
}
