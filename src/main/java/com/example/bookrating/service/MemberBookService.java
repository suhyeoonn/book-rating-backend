package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.dto.GetMyBooksDto;
import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Member;
import com.example.bookrating.entity.MemberBook;
import com.example.bookrating.repository.BookRepository;
import com.example.bookrating.repository.MemberBookRepository;
import com.example.bookrating.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberBookService {

    private final MemberBookRepository memberBookRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberBook create(CreateMemberBookDto createMemberBookDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Book book = bookRepository.findByIsbn(createMemberBookDto.getIsbn())
                .orElseGet(() -> saveNewBook(createMemberBookDto));

        // 이미 책이 등록되어 있는지 확인
        if (memberBookRepository.existsByMemberAndBook(member, book)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book is already registered.");
        }

        // 새로운 MemberBook 엔터티 생성 및 저장
        MemberBook memberBook = MemberBook.builder()
                .member(member)
                .book(book)
                .status(0)
                .build();
        return memberBookRepository.save(memberBook);
    }

    public List<GetMyBooksDto> findAll(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        List<MemberBook> memberBooks = memberBookRepository.findByMemberId(memberId);

        return memberBooks.stream()
                .map(memberBook -> new GetMyBooksDto(
                        memberBook.getId(),
                        memberBook.getStatus(),
                        memberBook.getCreatedAt(),
                        memberBook.getUpdatedAt(),
                        memberBook.getFinishedAt(),
                        new GetMyBooksDto.Book(
                                memberBook.getBook().getId(),
                                memberBook.getBook().getTitle()
                        ),
                        memberBook.getReview() != null ? memberBook.getReview().getRating() : 0
                ))
                .collect(Collectors.toList());
    }

    private Book saveNewBook(CreateMemberBookDto dto) {
        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .thumbnail(dto.getThumbnail())
                .contents(dto.getContents())
                .datetime(dto.getDatetime())
                .authors(dto.getAuthors())
                .publisher(dto.getPublisher())
                .build();
        return bookRepository.save(book);
    }
}