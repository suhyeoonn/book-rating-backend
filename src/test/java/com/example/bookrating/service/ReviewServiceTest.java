package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.ReviewDto;
import com.example.bookrating.dto.ReviewResponseDto;
import com.example.bookrating.entity.Review;
import com.example.bookrating.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;
    @Autowired
    BookService bookService;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void addReview() {
        int[] tagIds = {1, 3};
        BookDto book = new BookDto(null, "1234", "book", tagIds);
        BookDto savedBook = bookService.create(book);

        ReviewDto reviewDto = new ReviewDto(null, 4, "good", null);
        ReviewResponseDto result = reviewService.addReview(savedBook.getId(), reviewDto);

        assertEquals(result.getReview().getReviewText(), "good");
        assertEquals(4, result.getAverageRating());

        ReviewDto reviewDto2 = new ReviewDto(null, 1, "bad", null);
        ReviewResponseDto result2 = reviewService.addReview(savedBook.getId(), reviewDto2);

        assertEquals(2.5, result2.getAverageRating());
    }

    @Test
    void updateReview() {
        int[] tagIds = {1, 3};
        BookDto book = new BookDto(null, "1234", "book", tagIds);
        BookDto savedBook = bookService.create(book);

        ReviewDto reviewDto = new ReviewDto(null, 4, "good", null);
        ReviewResponseDto result = reviewService.addReview(savedBook.getId(), reviewDto);

        reviewService.updateReview(savedBook.getId(), result.getReview().getId(),
                new ReviewDto(null, null, "bad", null)
        );

        Review review = reviewRepository.findById(result.getReview().getId()).get();
        assertEquals("bad", review.getReviewText());

        ReviewResponseDto updateReview = reviewService.updateReview(savedBook.getId(), result.getReview().getId(),
                new ReviewDto(null, 3, "not bad", null)
        );

        Review review2 = reviewRepository.findById(result.getReview().getId()).get();
        assertEquals("not bad", review2.getReviewText());
        assertEquals(3, review2.getRating());
        assertEquals(3, updateReview.getAverageRating());
    }

    @Test
    void deleteReview() {
        int[] tagIds = {1, 3};
        BookDto book = new BookDto(null, "1234", "book", tagIds);
        BookDto savedBook = bookService.create(book);

        reviewService.addReview(savedBook.getId(), new ReviewDto(null, 4, "good", null));
        ReviewResponseDto addReview = reviewService.addReview(savedBook.getId(), new ReviewDto(null, 1, "bad", null));

        assertEquals(2.5, addReview.getAverageRating());

        ReviewResponseDto deleteReview = reviewService.deleteReview(savedBook.getId(), addReview.getReview().getId());
        assertEquals(4, deleteReview.getAverageRating());

        Review result = reviewRepository.findById(addReview.getReview().getId()).orElse(null);
        assertNull(result);
    }
}