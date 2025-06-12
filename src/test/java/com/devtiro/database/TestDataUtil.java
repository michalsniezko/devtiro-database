package com.devtiro.database;

import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

public final class TestDataUtil {

    public static Author createTestAuthorA() {
        return Author.builder()
                .name("John Doe")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("Abigail Rose")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .name("Jess McCormac")
                .age(24)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("987-1-2345-3453-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("983-5-2645-3453-3")
                .title("The Curvy Tomato")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("983-5-2345-3553-3")
                .title("Of Dice and Ben")
                .author(author)
                .build();
    }
}
