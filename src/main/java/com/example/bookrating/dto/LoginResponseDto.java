package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    private User user;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class User {
        private Long id;
        private String username;
    }
}
