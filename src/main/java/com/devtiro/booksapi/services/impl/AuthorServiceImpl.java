package com.devtiro.booksapi.services.impl;

import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.repositories.AuthorRepository;
import com.devtiro.booksapi.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean doesNotExist(Long id) {
        return !authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRepository.findById(authorEntity.getId()).map(
                existingAuthor -> {
                    Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
                    Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
                    return authorRepository.save(existingAuthor);
                }
        ).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
