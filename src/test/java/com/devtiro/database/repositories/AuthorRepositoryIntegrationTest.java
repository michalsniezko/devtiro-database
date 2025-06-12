package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {
    private final AuthorRepository underTest;

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result)
                .isPresent()
                .get()
                .isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        authorA.setName("UPDATED");
        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent().get().isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeletedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        underTest.delete(authorA);
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        underTest.saveAll(List.of(authorA, authorB, authorC));

        Iterable<Author> results = underTest.ageLessThan(50);
        assertThat(results).containsExactly(authorB, authorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        underTest.saveAll(List.of(authorA, authorB, authorC));

        Iterable<Author> results = underTest.findAuthorsWithAgeGreaterThan(50);
        assertThat(results).containsExactly(authorA);
    }
}
