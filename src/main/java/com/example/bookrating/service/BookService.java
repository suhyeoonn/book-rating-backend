package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(BookDto book) {
        // isbn이 동일한 책은 중복 등록할 수 없음
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(e -> {
            throw new IllegalStateException("이미 존재하는 책입니다");
        });;

        return bookRepository.save(book.toEntity());
    }
}
