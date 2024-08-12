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

    public Book create(BookDto book) {
        // isbn이 동일한 책은 중복 등록할 수 없음
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(e -> {
            throw new IllegalStateException("이미 존재하는 책입니다");
        });;

        return bookRepository.save(book.toEntity());
    }

    public Book update(Integer id, BookDto dto) {
        Book target = findBookOrThrow(id);

        Book book = dto.toEntity();
        target.patch(book);

        return bookRepository.save(target);
    }

    public void delete(Integer id) {
        findBookOrThrow(id);

        bookRepository.deleteById(id);
    }

    public Book findBookOrThrow(Integer id) {
        Book target = bookRepository.findById(id).orElse(null);
        if (target == null) {
            throw new IllegalStateException("존재하지 않는 책입니다");
        }
        return target;
    }
}
