package com.example.bookrating.service;

import com.example.bookrating.dto.BookDto;
import com.example.bookrating.dto.CreateMemberBookDto;
import com.example.bookrating.dto.GetMyBookDto;
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

    public boolean exists(String isbn, Long memberId) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        return memberBookRepository.existsByMemberIdAndBookId(memberId, book.getId());
    }

    public GetMyBookDto find(Long myBookId) {
        MemberBook memberBook = memberBookRepository.findById(myBookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        GetMyBookDto.BookDto bookDto = new GetMyBookDto.BookDto(
                memberBook.getBook().getId(),
                memberBook.getBook().getIsbn(),
                memberBook.getBook().getTitle(),
                memberBook.getBook().getThumbnail(),
                memberBook.getBook().getContents(),
                memberBook.getBook().getDatetime(),
                memberBook.getBook().getAuthors(),
                memberBook.getBook().getPublisher()
        );

        GetMyBookDto.ReviewDto reviewDto = memberBook.getReview() != null ?
                new GetMyBookDto.ReviewDto(
                        memberBook.getReview().getId(),
                        memberBook.getReview().getRating()
                ) : null;

        return new GetMyBookDto(
                memberBook.getId(),
                memberBook.getStatus(),
                memberBook.getCreatedAt(),
                memberBook.getUpdatedAt(),
                memberBook.getFinishedAt(),
                memberBook.getMemo(),
                bookDto,
                reviewDto
        );
    }

    @Transactional
    public MemberBook updateMemo(Long memberBookId, String memo) {
        MemberBook memberBook = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        memberBook.setMemo(memo);
        return memberBookRepository.save(memberBook);
    }

    @Transactional
    public MemberBook updateStatus(Long memberBookId, int status) {
        MemberBook memberBook = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        memberBook.setStatus(status);
        return memberBookRepository.save(memberBook);
    }

    @Transactional
    public void deleteMemberBook(Long memberBookId) {
        MemberBook memberBook = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        memberBookRepository.delete(memberBook);
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
                .url(dto.getUrl())
                .build();
        return bookRepository.save(book);
    }
}