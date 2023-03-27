package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MovieRepository;
import com.kenzie.appserver.repositories.model.MovieRecord;
import com.kenzie.appserver.service.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findById(String movieId) {
        Movie movieFromBackend = movieRepository
                .findById(movieId)
                .map(movie -> new Movie(movie.getMovieId(), movie.getTitle(), movie.getDescription()))
                .orElse(null);

        return movieFromBackend;
    }

    public Movie addNewMovie(Movie movie) {
        MovieRecord movieRecord = new MovieRecord();
        movieRecord.setMovieId(movie.getMovieId());
        movieRecord.setTitle(movie.getTitle());
        movieRecord.setDescription(movie.getDescription());
        movieRepository.save(movieRecord);
        return movie;
    }

    public List<MovieRecord> findAll() {
        List<MovieRecord> movieFromBackend = (List<MovieRecord>) movieRepository
                .findAll();

        return movieFromBackend;
    }
}
