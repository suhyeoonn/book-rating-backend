package com.example.bookrating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeDto {

    private User user;
    private String token;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class User {  // static 추가
        private Long id;        // int → Long 변경
        private String username;
    }
}

