package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class GetMyBooksDto {
    private Long id;
    private int status;
    private Date createdAt;
    private Date updatedAt;
    private Date finishedAt;
    private Book book;
    private int rating;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Book {
        private Long id;
        private String title;
    }
}


