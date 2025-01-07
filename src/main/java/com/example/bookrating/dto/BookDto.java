package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String thumbnail;
    private String contents;
    private Date datetime;
    private String authors;
    private String publisher;
    private Integer averageRating;
    private Integer reviewCount;
}