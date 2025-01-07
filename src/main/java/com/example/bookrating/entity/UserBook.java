package com.example.bookrating.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    // 하나의 책은 여러명이 등록할 수 있다.
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // 하나의 UserBook은 하나의 Review와 연결
    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private int status = 0;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date finishedAt;

    // 엔터티 저장 시 업데이트 시간 자동 갱신
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
