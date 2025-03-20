package com.example.bookrating.repository;

import com.example.bookrating.dto.ReviewSummaryDto;
import com.example.bookrating.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByBookId(Long bookId);

    List<Review> findReviewByIsbn(String isbn);

    @Query("SELECT new com.example.bookrating.dto.ReviewSummaryDto( " +
            "COALESCE(AVG(r.rating), 0), COUNT(r)) " +
            "FROM Review r WHERE r.book.id = :bookId")
    Optional<ReviewSummaryDto> findReviewSummaryByBookId(@Param("bookId") Long bookId);

    @Query("SELECT new com.example.bookrating.dto.ReviewSummaryDto( " +
            "COALESCE(AVG(r.rating), 0), COUNT(r)) " +
            "FROM Review r WHERE r.isbn = :isbn")
    Optional<ReviewSummaryDto> findReviewSummaryByIsbn(@Param("isbn") String isbn);
}
