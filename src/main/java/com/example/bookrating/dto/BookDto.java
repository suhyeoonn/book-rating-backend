package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

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
    private OffsetDateTime datetime;
    private String authors;
    private String publisher;
    private Integer averageRating;
    private Integer reviewCount;

    public BookDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}