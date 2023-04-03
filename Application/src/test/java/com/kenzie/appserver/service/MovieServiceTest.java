package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MovieRepository;
import com.kenzie.appserver.repositories.model.MovieRecord;
import com.kenzie.appserver.service.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setup() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByMovieId() {
        // GIVEN
        String id = randomUUID().toString();

        MovieRecord movieRecord = new MovieRecord();
        movieRecord.setMovieId(id);
        movieRecord.setTitle("ABC");
        movieRecord.setDescription("XYZ");

        // WHEN
        when(movieRepository.findById(id)).thenReturn(Optional.of(movieRecord));
        Movie movie = movieService.findById(id);

        // THEN
        Assertions.assertNotNull(movie, "The movie is returned");
        assertEquals(movieRecord.getMovieId(), movie.getMovieId(), "The id matches");
        assertEquals(movieRecord.getTitle(), movie.getTitle(), "The title matches");
        assertEquals(movieRecord.getDescription(), movie.getDescription(), "The description matches");
    }


    @Test
    void addNewMovie() {
        // GIVEN
        String id = randomUUID().toString();

        Movie movie = new Movie(id, "ABC", "XYZ");

        MovieRecord movieRecord = new MovieRecord();
        movieRecord.setMovieId(id);
        movieRecord.setTitle("ABC");
        movieRecord.setDescription("XYZ");

        // WHEN
        when(movieRepository.save(movieRecord)).thenReturn(movieRecord);

        Movie result = movieService.addNewMovie(movie);

        //THEN
        verify(movieRepository).save(movieRecord);
        assertEquals(id, movieRecord.getMovieId());
        assertEquals("ABC", movieRecord.getTitle());
        assertEquals("XYZ", movieRecord.getDescription());
        Assertions.assertSame(movie, result);
    }

    @Test
    public void findAllMovies() {

        //GIVEN
        List<MovieRecord> movies = new ArrayList<>();
        MovieRecord movie1 = new MovieRecord();
        movie1.setMovieId("1");
        movie1.setTitle("ABC");
        movie1.setDescription("XYZ");
        movies.add(movie1);
        MovieRecord movie2 = new MovieRecord();
        movie2.setMovieId("2");
        movie2.setTitle("AABB");
        movie2.setDescription("YYZZ");
        movies.add(movie2);

        //WHEN
        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieRecord> allMovies = movieService.findAll();

        // THEN
        assertEquals(movies.size(), allMovies.size());
        Assertions.assertTrue(allMovies.contains(movie1));
        Assertions.assertTrue(allMovies.contains(movie2));
        }
    }
