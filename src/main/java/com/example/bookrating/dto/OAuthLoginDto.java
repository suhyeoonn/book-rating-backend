package com.example.bookrating.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthLoginDto {
    private String provider;
    private String code;
}