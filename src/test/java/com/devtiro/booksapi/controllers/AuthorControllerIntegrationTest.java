package com.devtiro.booksapi.controllers;

import com.devtiro.booksapi.TestDataUtil;
import com.devtiro.booksapi.domain.dto.AuthorDto;
import com.devtiro.booksapi.domain.entities.AuthorEntity;
import com.devtiro.booksapi.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                ).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(80));
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(get("/authors")).andExpect(status().isOk());
    }

    @Test
    public void testListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthorA);

        mockMvc.perform(get("/authors"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(80));

    }

    @Test
    public void testThatGetAuthorHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthorA);

        mockMvc.perform(get("/authors/1")).andExpect(status().isOk());
    }

    @Test
    public void testThatGetAuthorHttpStatus400WhenAuthorDoesNotExist() throws Exception {
        mockMvc.perform(get("/authors/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testThatGetAuthorReturnAuthorWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthorA);

        mockMvc.perform(get("/authors/1"))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(80));
    }

    @Test
    public void testThatFullUpdateAuthorHttpStatus400WhenAuthorDoesNotExist() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        String json = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntity = authorService.saveAuthor(testAuthorA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String json = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                put("/authors/" + authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntity = authorService.saveAuthor(testAuthorA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setId(authorEntity.getId());
        String json = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(put("/authors/" + authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(jsonPath("$.name").value(testAuthorDtoA.getName()))
                .andExpect(jsonPath("$.age").value(testAuthorDtoA.getAge()))
                .andExpect(jsonPath("$.id").value(testAuthorDtoA.getId())
                );
    }

}
