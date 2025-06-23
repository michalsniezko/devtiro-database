package com.devtiro.booksapi;

import com.devtiro.booksapi.domain.dto.AuthorDto;
import com.devtiro.booksapi.domain.dto.BookDto;
import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.domain.entities.BookEntity;

public final class TestDataUtil {

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .name("John Doe")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .name("John Doe")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .name("Abigail Rose")
                .age(44)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .name("Jess McCormac")
                .age(24)
                .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("987-1-2345-3453-0")
                .title("The Shadow in the Attic")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("987-1-2345-3453-0")
                .title("The Shadow in the Attic")
                .author(authorDto)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("983-5-2645-3453-3")
                .title("The Curvy Tomato")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("983-5-2345-3553-3")
                .title("Of Dice and Ben")
                .authorEntity(authorEntity)
                .build();
    }
}
