package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {
    private final BookRepository underTest;
    private final AuthorRepository authorRepository;

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorRepository.save(author);

        Book book = TestDataUtil.createTestBookA(savedAuthor);
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent().get().isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBookA(savedAuthor);
        underTest.save(bookA);
        Book bookB = TestDataUtil.createTestBookB(savedAuthor);
        underTest.save(bookB);
        Book bookC = TestDataUtil.createTestBookC(savedAuthor);
        underTest.save(bookC);

        Iterable<Book> result = underTest.findAll();

        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBookA(savedAuthor);
        underTest.save(bookA);

        bookA.setTitle("updated title");
        underTest.save(bookA);

        Optional<Book> result = underTest.findById(bookA.getIsbn());
        Assertions.assertThat(result).isPresent().get().isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeletedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorRepository.save(author);
        Book bookA = TestDataUtil.createTestBookA(savedAuthor);
        underTest.save(bookA);
        underTest.delete(bookA);
        Optional<Book> result = underTest.findById(bookA.getIsbn());

        Assertions.assertThat(result).isNotPresent();
    }
}
