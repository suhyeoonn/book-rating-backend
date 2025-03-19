package com.example.bookrating.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "review_levels", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private Set<Level> levels = new HashSet<>();

    public Review(Book book, Integer rating, String comment, Member member, Set<Level> levels) {
        this.book = book;
        this.rating = rating;
        this.comment = comment;
        this.member = member;
        this.levels = levels;
    }



    public Review(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void patch(Review review) {
        if (review.comment != null) {
            this.comment = review.comment;
        }
        if (review.rating != null) {
            this.rating = review.rating;
        }
        if (review.levels != null) {
            this.levels = review.levels;
        }
    }
}
