package com.example.bookrating.controller;

import com.example.bookrating.dto.TagDto;
import com.example.bookrating.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

}
