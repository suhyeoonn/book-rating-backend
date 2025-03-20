package com.example.bookrating.repository;

import com.example.bookrating.dto.PopularBookDto;
import com.example.bookrating.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT new com.example.bookrating.dto.PopularBookDto(b.id, b.title, b.isbn, b.thumbnail, AVG(r.rating), COUNT(r.id)) " +
            "FROM Review r JOIN r.book b " +
            "GROUP BY b.id, b.title " +
            "HAVING COUNT(r.id) >= :minReviews " +
            "ORDER BY COUNT(r.id) DESC, AVG(r.rating) DESC")
    List<PopularBookDto> findPopularBooks(@Param("minReviews") long minReviews);

    @Query("SELECT new com.example.bookrating.dto.PopularBookDto(" +
            "b.id, b.title, b.isbn, b.thumbnail, " +
            "COALESCE(AVG(r.rating), 0), COUNT(DISTINCT r.id)) " +
            "FROM MemberBook mb " +
            "JOIN mb.book b " +
            "LEFT JOIN Review r ON r.book.id = b.id " +
            "GROUP BY b.id, b.title, b.isbn, b.thumbnail " +
            "ORDER BY COUNT(mb.book.id) DESC")
    List<PopularBookDto> findMostAddedBooks();
}
