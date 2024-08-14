package com.example.bookrating.dto;

import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDto {
    private Integer id;
    private String isbn;
    private String title;
    private int[] tagIds;

    public Book toEntity(Set<Tag> tags) {
        return new Book(id, isbn, title, tags);
    }
}
