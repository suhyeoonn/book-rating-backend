package com.example.bookrating.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSummaryDto {
    private double averageRating;
    private long reviewCount;

    public ReviewSummaryDto(double averageRating, long reviewCount) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }
}
