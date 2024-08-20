package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookListDto {
    private Integer id;
    private String isbn;
    private String title;
    private List<TagDto> tags;
}