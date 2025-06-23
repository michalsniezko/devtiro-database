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
import java.util.Optional;
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
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
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

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(
                authorEntity -> {
                    AuthorDto authorDto = authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                }
        ).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if(!authorService.isExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        authorDto.setId(id);
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorMapper.mapFrom(authorDto));

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if(!authorService.isExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }
}
