package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.MovieCreateRequest;
import com.kenzie.appserver.controller.model.MovieResponse;
import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;

    MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable("movieId") String movieId) {

        Movie movie = movieService.findById(movieId);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setMovieId(movie.getMovieId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());
        return ResponseEntity.ok(movieResponse);
    }

    @PostMapping("/movie/add")
    public ResponseEntity<MovieResponse> addNewConcert(@RequestBody MovieCreateRequest movieCreateRequest) {
        Movie movie = new Movie(randomUUID().toString(),
                movieCreateRequest.getTitle(),
                movieCreateRequest.getDescription());
        movieService.addNewMovie(movie);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setMovieId(movie.getMovieId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());

        return ResponseEntity.created(URI.create("/movie/" + movieResponse.getTitle())).body(movieResponse);
    }
}