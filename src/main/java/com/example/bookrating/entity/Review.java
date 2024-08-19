package com.example.bookrating.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public Review(Book book, Integer rating, String reviewText) {
        this.book = book;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void patch(Review review) {
        if (review.reviewText != null) {
            this.reviewText = review.reviewText;
        }
        if (review.rating != null) {
            this.rating = review.rating;
        }
    }
}
