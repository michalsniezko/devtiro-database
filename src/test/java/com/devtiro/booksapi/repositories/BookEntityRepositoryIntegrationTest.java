package com.devtiro.booksapi.repositories;

import com.devtiro.booksapi.TestDataUtil;
import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.domain.entities.BookEntity;
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
public class BookEntityRepositoryIntegrationTest {
    private final BookRepository underTest;
    private final AuthorRepository authorRepository;

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);

        BookEntity bookEntity = TestDataUtil.createTestBookA(savedAuthorEntity);
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent().get().isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);

        BookEntity bookEntityA = TestDataUtil.createTestBookA(savedAuthorEntity);
        underTest.save(bookEntityA);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(savedAuthorEntity);
        underTest.save(bookEntityB);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(savedAuthorEntity);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();

        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);

        BookEntity bookEntityA = TestDataUtil.createTestBookA(savedAuthorEntity);
        underTest.save(bookEntityA);

        bookEntityA.setTitle("updated title");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        Assertions.assertThat(result).isPresent().get().isEqualTo(bookEntityA);
    }

    @Test
    public void testThatBookCanBeDeletedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        BookEntity bookEntityA = TestDataUtil.createTestBookA(savedAuthorEntity);
        underTest.save(bookEntityA);
        underTest.delete(bookEntityA);
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());

        Assertions.assertThat(result).isNotPresent();
    }
}
