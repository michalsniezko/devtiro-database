package com.devtiro.booksapi.controllers;

import com.devtiro.booksapi.domain.dto.BookDto;
import com.devtiro.booksapi.domain.entities.BookEntity;
import com.devtiro.booksapi.mappers.Mapper;
import com.devtiro.booksapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }
}
