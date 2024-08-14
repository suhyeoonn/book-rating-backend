package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Tag;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book create(BookDto dto) {
        // isbn이 동일한 책은 중복 등록할 수 없음
        bookRepository.findByIsbn(dto.getIsbn()).ifPresent(e -> {
            throw new IllegalStateException("이미 존재하는 책입니다");
        });;


        Set<Tag> tags = new HashSet<>();
        for (int tagId: dto.getTagIds()) {
            Optional<Tag> tag = tagRepository.findById(tagId);
            if (tag.isPresent()) {
                tags.add(tag.get());
            }
        }

        Book book = dto.toEntity(tags);

        return bookRepository.save(book);
    }

    public Book update(Integer id, BookDto dto) {
        Book target = findBookOrThrow(id);

        Book book = dto.toEntity(null);
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
