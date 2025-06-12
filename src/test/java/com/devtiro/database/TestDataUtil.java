package com.devtiro.database;

import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

public final class TestDataUtil {

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("John Doe")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Abigail Rose")
                .age(54)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Jess McCormac")
                .age(25)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("987-1-2345-3453-0")
                .title("The Shadow in the Attic")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("983-5-2645-3453-3")
                .title("The Curvy Tomato")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("983-5-2345-3553-3")
                .title("Of Dice and Ben")
                .authorId(1L)
                .build();
    }
}
