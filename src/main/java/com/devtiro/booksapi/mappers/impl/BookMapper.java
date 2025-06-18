package com.devtiro.booksapi.mappers.impl;

import com.devtiro.booksapi.domain.dto.BookDto;
import com.devtiro.booksapi.domain.entities.BookEntity;
import com.devtiro.booksapi.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper implements Mapper<BookEntity, BookDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
