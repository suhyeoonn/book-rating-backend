package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;

    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .title(book.getTitle())
                        .thumbnail(book.getThumbnail())
                        .contents(book.getContents())
                        .datetime(book.getDatetime())
                        .authors(book.getAuthors())
                        .publisher(book.getPublisher())
                        .build())
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " not found."));

        return BookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .thumbnail(book.getThumbnail())
                .contents(book.getContents())
                .datetime(book.getDatetime())
                .authors(book.getAuthors())
                .publisher(book.getPublisher())
                .build();
    }

    public List<BookDto> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);

        return books.stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .title(book.getTitle())
                        .thumbnail(book.getThumbnail())
                        .contents(book.getContents())
                        .datetime(book.getDatetime())
                        .authors(book.getAuthors())
                        .publisher(book.getPublisher())
                        .build())
                .collect(Collectors.toList());
    }

}
