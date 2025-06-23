package com.devtiro.booksapi.controllers;

import com.devtiro.booksapi.TestDataUtil;
import com.devtiro.booksapi.domain.dto.BookDto;
import com.devtiro.booksapi.domain.entities.BookEntity;
import com.devtiro.booksapi.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Test
    public void testThatCreateUpdateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedUpdateBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    public void testThatUpdateBookReturns200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBookEntity.getIsbn());

        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(savedBookEntity.getTitle()));
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook("123123123", bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(bookEntity.getTitle())
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200OkWhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook("123123123", bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesNotExist() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatusOkWhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook("123123123", bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle("UPDATED");
        String updateBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateBookUpdatesExistingBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook("123123123", bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle("UPDATED");
        String updateBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"));
    }
}
