package com.example.bookrating.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateMemberBookDto {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String thumbnail;

    @NotBlank
    private String contents;

    @PastOrPresent
    private LocalDateTime datetime;

    @NotBlank
    private String authors;

    @NotBlank
    private String publisher;
}