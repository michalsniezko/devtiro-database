package com.devtiro.booksapi.services.impl;

import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.repositories.AuthorRepository;
import com.devtiro.booksapi.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .toList();
    }
}
