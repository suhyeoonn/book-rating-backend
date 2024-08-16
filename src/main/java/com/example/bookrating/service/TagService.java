package com.example.bookrating.service;

import com.example.bookrating.dto.TagDto;
import com.example.bookrating.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<TagDto> getTags() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }
}
