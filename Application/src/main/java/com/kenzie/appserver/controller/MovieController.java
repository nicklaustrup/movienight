package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.MovieCreateRequest;
import com.kenzie.appserver.controller.model.MovieResponse;
import com.kenzie.appserver.repositories.model.MovieRecord;
import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.model.Movie;
import org.apache.http.client.utils.URIUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<MovieResponse> addNewMovie(@RequestBody MovieCreateRequest movieCreateRequest
                                                     /*UriComponentsBuilder uriComponentsBuilder*/) {
        Movie movie = new Movie(randomUUID().toString(),
                movieCreateRequest.getTitle(),
                movieCreateRequest.getDescription());
        movieService.addNewMovie(movie);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setMovieId(movie.getMovieId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());

        // Build the URI using UriComponentsBuilder
//        URI location = uriComponentsBuilder
//                .path("/movie/{title}")
//                .buildAndExpand(movieResponse.getTitle())
//                .toUri();
        return ResponseEntity.created(URI.create("/movie/" + movieResponse.getMovieId())).body(movieResponse);
//        return ResponseEntity.created(location).body(movieResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        List<MovieRecord> allMovies = movieService.findAll();

        if (allMovies.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        List<MovieResponse> allMoviesResponse = new ArrayList<>();

        for (MovieRecord record: allMovies) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setMovieId(record.getMovieId());
            movieResponse.setTitle(record.getTitle());
            movieResponse.setDescription(record.getDescription());
            allMoviesResponse.add(movieResponse);
        }

        return ResponseEntity.ok(allMoviesResponse);
    }
}
