package com.example.bookrating.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column
    private String email;

    public Member(String username, String password, String provider, String providerId, String email) {
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }
}