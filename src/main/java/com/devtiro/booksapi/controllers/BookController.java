package com.devtiro.booksapi.controllers;

import com.devtiro.booksapi.domain.dto.BookDto;
import com.devtiro.booksapi.domain.entities.BookEntity;
import com.devtiro.booksapi.mappers.Mapper;
import com.devtiro.booksapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);

        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return bookExists ?
                new ResponseEntity<>(savedBookDto, HttpStatus.OK) :
                new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<BookDto> findAllBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities
                .stream()
                .map(bookMapper::mapTo)
                .toList();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
                    BookDto bookDto = bookMapper.mapTo(bookEntity);
                    return new ResponseEntity<>(bookDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);

        BookDto updatedBookDto = bookMapper.mapTo(updatedBookEntity);
        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }
}
