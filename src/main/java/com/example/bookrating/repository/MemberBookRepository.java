package com.example.bookrating.repository;

import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Member;
import com.example.bookrating.entity.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
    boolean existsByMemberAndBook(Member member, Book book);
    List<MemberBook> findByMemberId(Long memberId);
    boolean existsByMemberIdAndBookId(Long memberId, Long bookId);
}