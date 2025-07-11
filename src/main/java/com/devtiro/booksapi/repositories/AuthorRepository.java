package com.devtiro.booksapi.repositories;

import com.devtiro.booksapi.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("select a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int i);
}

