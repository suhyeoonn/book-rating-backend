package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Tag;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TagRepository tagRepository;

    int[] tagIds = {1,3};

    @Test
    void 책_등록() {
        BookDto book = new BookDto(null, "1234", "book", tagIds);

        bookService.create(book);

        Book result = bookRepository.findByIsbn(book.getIsbn()).get();

        assertEquals(result.getIsbn(), book.getIsbn());

        assertEquals(tagIds.length, result.getTags().size());
    }

    @Test
    void isbn_중복_확인() {
        BookDto book1 = new BookDto(null, "1234", "book", tagIds);
        bookService.create(book1);

        BookDto book2 = new BookDto(null, "1234", "book", tagIds);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> bookService.create(book2));

        assertEquals(e.getMessage(), "이미 존재하는 책입니다");
    }

    @Test
    void 책_제목_수정() {
        BookDto book1 = new BookDto(null, "1234", "book", tagIds);
        Book saveBook = bookService.create(book1);

        BookDto dto = new BookDto(null, null, "update", tagIds);
        bookService.update(saveBook.getId(), dto);

        Book result = bookRepository.findById(saveBook.getId()).get();
        assertEquals(result.getTitle(), dto.getTitle());
    }

    @Test
    void 책_삭제() {
        BookDto book1 = new BookDto(null, "1234", "book", tagIds);
        Book saveBook = bookService.create(book1);

        bookService.delete(saveBook.getId());

        Book result = bookRepository.findById(saveBook.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    void 존재하지_않는_책_확인() {
        long count = bookRepository.count();

        int id = (int) (count + 1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> bookService.findBookOrThrow(id));

        assertEquals(e.getMessage(), "존재하지 않는 책입니다");
    }
}