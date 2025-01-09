package com.example.bookrating.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewListResponseDto {
    private List<Review> reviews;
    private double averageRating;

    @Getter
    @Setter
    @Builder
    public static class Review {
        private Long id;
        private Integer rating;
        private String comment;
        private LocalDateTime updatedAt;
        private User user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class User {
        private Long id;
        private String username;
    }
}





