package com.example.bookrating.repository;

import com.example.bookrating.entity.Book;
import com.example.bookrating.entity.Member;
import com.example.bookrating.entity.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
    boolean existsByMemberAndBook(Member member, Book book);
}