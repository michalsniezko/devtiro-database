package com.devtiro.booksapi.services.impl;

import com.devtiro.booksapi.domain.entities.BookEntity;
import com.devtiro.booksapi.repositories.BookRepository;
import com.devtiro.booksapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
}
