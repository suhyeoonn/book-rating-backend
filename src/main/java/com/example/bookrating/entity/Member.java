package com.example.bookrating.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 일대다 관계: 한 사용자는 여러 리뷰를 작성할 수 있음
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews;
}