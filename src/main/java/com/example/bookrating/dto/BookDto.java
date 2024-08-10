package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDto {
    private Integer id;
    private String isbn;
    private String title;
}
