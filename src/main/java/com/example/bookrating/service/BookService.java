package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.BookListDto;
import com.example.bookrating.dto.TagDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Tag;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;

    public List<BookListDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> BookListDto.builder()
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

    public BookListDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " not found."));

        return BookListDto.builder()
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

    public List<BookListDto> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);

        return books.stream()
                .map(book -> BookListDto.builder()
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
