package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.BookListDto;
import com.example.bookrating.dto.TagDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Tag;
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

    public List<BookListDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> {
                    List<TagDto> tags = book.getTags().stream()
                            .map(tag -> new TagDto(tag.getId(), tag.getName()))
                            .toList();
                    return new BookListDto(book.getId(), book.getIsbn(), book.getTitle(), tags);
                }).collect(Collectors.toList());
    }

    private BookDto toDto(Book book) {
        int[] tagIds = book.getTags().stream()
                .mapToInt(Tag::getId)
                .toArray();
        return new BookDto(book.getId(), book.getIsbn(), book.getTitle(), tagIds);
    }

    public BookDto create(BookDto dto) {
        // isbn이 동일한 책은 중복 등록할 수 없음
        bookRepository.findByIsbn(dto.getIsbn()).ifPresent(e -> {
            throw new IllegalStateException("이미 존재하는 책입니다");
        });

        Set<Tag> tags = findTagsByIds(dto.getTagIds());

        Book saved = bookRepository.save(dto.toEntity(tags));

        return toDto(saved);
    }

    private Set<Tag> findTagsByIds(int[] tagIds) {
        Set<Tag> tags = new HashSet<>();
        for (int tagId : tagIds) {
            Optional<Tag> tag = tagRepository.findById(tagId);
            if (tag.isPresent()) {
                tags.add(tag.get());
            }
        }
        return tags;
    }

    public BookDto update(Integer id, BookDto dto) {
        Book target = findBookOrThrow(id);

        Set<Tag> tags = Optional.ofNullable(dto.getTagIds())
                .map(this::findTagsByIds)
                .orElse(Collections.emptySet());

        Book book = dto.toEntity(tags);

        target.patch(book);

        return toDto(bookRepository.save(target));
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
