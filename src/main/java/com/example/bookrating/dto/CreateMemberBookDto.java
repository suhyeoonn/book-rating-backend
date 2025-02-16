package com.example.bookrating.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreateMemberBookDto {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String thumbnail;

    private String contents;

    @PastOrPresent
    private OffsetDateTime datetime;

    @NotBlank
    private String authors;

    private String publisher;

    @NotBlank
    private String url;

    private int status;
}