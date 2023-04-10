package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.MovieCreateRequest;
import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.model.Movie;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    MovieService movieService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        String id = UUID.randomUUID().toString();
        String title = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();

        Movie movie = new Movie(id, title, description);
        Movie persistedMovie = movieService.addNewMovie(movie);
        mvc.perform(get("/movie/{id}", persistedMovie.getMovieId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(status().isOk());
    }

    @Test
    public void createMovie_CreateSuccessful() throws Exception {
        String title = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();

        MovieCreateRequest movieCreateRequest = new MovieCreateRequest();
        movieCreateRequest.setTitle(title);
        movieCreateRequest.setDescription(description);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/movie")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(movieCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(status().isCreated());
    }
}