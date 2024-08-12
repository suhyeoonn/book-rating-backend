package com.example.bookrating.dto;

import com.example.bookrating.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDto {
    private Integer id;
    private String isbn;
    private String title;

    public Book toEntity() {
        return new Book(id, isbn, title);
    }
}
