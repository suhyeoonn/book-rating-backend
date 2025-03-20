package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularBookDto {
    private Long id;
    private String title;
    private String isbn;
    private String thumbnail;
    private Double averageRating;
    private Long reviewCount;
}