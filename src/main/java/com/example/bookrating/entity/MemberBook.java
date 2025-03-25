package com.example.bookrating.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member와 다대일 관계
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 하나의 책은 여러명이 등록할 수 있다.
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // 하나의 UserBook은 하나의 Review와 연결
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private ReadingStatus status = ReadingStatus.READY;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @Column
    private OffsetDateTime finishedAt;

    // 엔터티 저장 시 업데이트 시간 자동 갱신
    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
