package com.devtiro.booksapi.controllers;

import com.devtiro.booksapi.domain.dto.AuthorDto;
import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.mappers.Mapper;
import com.devtiro.booksapi.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        return authorEntities
                .stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }
}
