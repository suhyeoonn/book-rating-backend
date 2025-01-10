package com.example.bookrating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class BookDto implements Serializable {
    private Long id;
    private String isbn;
    private String title;
    private String thumbnail;
    private String contents;
    private OffsetDateTime datetime;
    private String authors;
    private String publisher;
    private String url;
    private Double averageRating;
    private Long reviewCount;

    public BookDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}