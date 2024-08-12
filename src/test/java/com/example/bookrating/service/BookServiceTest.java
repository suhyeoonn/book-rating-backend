package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @Test
    void 책_등록() {
        BookDto book = new BookDto(null, "1234", "book");

        bookService.createBook(book);

        Book result = bookRepository.findByIsbn(book.getIsbn()).get();

        assertEquals(result.getIsbn(), book.getIsbn());
    }

    @Test
    void isbn_중복_확인() {
        BookDto book1 = new BookDto(null, "1234", "book");
        bookService.createBook(book1);

        BookDto book2 = new BookDto(null, "1234", "book");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> bookService.createBook(book2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 책입니다");
    }
}