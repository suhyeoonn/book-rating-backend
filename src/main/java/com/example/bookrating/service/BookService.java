package com.example.bookrating.service;

import com.example.bookrating.dto.*;
import com.example.bookrating.entity.Book;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.bookrating.service.MemberBookService.BOOK_CACHE_PREFIX;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ReviewService reviewService;

    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public BookService(BookRepository bookRepository,
                       TagRepository tagRepository,
                       ReviewService reviewService,
                       RedisTemplate<String, Object> redisTemplate) {
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
        this.reviewService = reviewService;
        this.redisTemplate = redisTemplate;
    }

    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::getBookDto).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        // opsForValue(): Redis에서 String 타입 데이터를 다룰 때 사용하는 API
        Object cachedObject = redisTemplate.opsForValue().get(BOOK_CACHE_PREFIX + id);
        if (cachedObject != null) {
            BookDto cachedBook = objectMapper.convertValue(cachedObject, BookDto.class);
            setReviewSummary(cachedBook);
            System.out.println("cache hit: "+ cachedBook);
            return cachedBook;
        }

        System.out.println("cache miss: " + id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + id + " not found."));

        BookDto bookDto = convertToBookDto(book);
        redisTemplate.opsForValue().set(BOOK_CACHE_PREFIX + id,  bookDto);

        setReviewSummary(bookDto);
        return bookDto;
    }

    public List<BookDto> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);

        return books.stream()
                .map(this::getBookDto).collect(Collectors.toList());
    }

    private BookDto convertToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .thumbnail(book.getThumbnail())
                .contents(book.getContents())
                .datetime(book.getDatetime())
                .authors(book.getAuthors())
                .publisher(book.getPublisher())
                .url(book.getUrl())
                .build();
    }

    private void setReviewSummary(BookDto bookDto) {
        ReviewSummaryDto reviewSummary = reviewService.getReviewSummary(bookDto.getId());
        bookDto.setAverageRating(reviewSummary.getAverageRating());
        bookDto.setReviewCount(reviewSummary.getReviewCount());
    }

    private BookDto getBookDto(Book book) {
        BookDto bookDto = convertToBookDto(book);
        setReviewSummary(bookDto);
        return bookDto;
    }

    public List<PopularBookDto> getPopularBooks(long minReviews) {
        return bookRepository.findPopularBooks(minReviews);
    }

    public List<PopularBookDto> getMostAddedBooks() {
        return bookRepository.findMostAddedBooks();
    }
}
