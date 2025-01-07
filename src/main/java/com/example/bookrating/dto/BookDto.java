package com.example.bookrating.dto;

import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Review;
import com.example.bookrating.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String thumbnail;
    private Integer averageRating;
    private Integer reviewCount;
    private String contents;
    private Date datetime;
    private String authors;
    private String publisher;


    public Book toEntity() {
        return new Book(id, isbn, title, thumbnail, contents, datetime, authors, publisher);
    }
}
