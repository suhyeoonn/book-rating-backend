package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewDto {
    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime updatedAt;
    private Long[] levels;
}
