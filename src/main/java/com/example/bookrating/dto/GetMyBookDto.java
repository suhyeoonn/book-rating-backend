package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class GetMyBookDto {
    private Long id;
    private int status;
    private Date createdAt;
    private Date updatedAt;
    private Date finishedAt;
    private String memo;
    private BookDto book;
    private ReviewDto review;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class BookDto {
        private Long id;
        private String isbn;
        private String title;
        private String thumbnail;
        private String contents;
        private OffsetDateTime datetime;
        private String authors;
        private String publisher;
        private String url;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReviewDto {
        private Long id;
        private int rating;
    }
}
