package com.kenzie.appserver.service.model;

public class Movie {
    private final String movieId;
    private final String title;
    private final String description;

    public Movie(String movieId, String title, String description) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {return description;}
}
