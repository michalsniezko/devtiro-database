package com.devtiro.booksapi.services;

import com.devtiro.booksapi.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);
}
