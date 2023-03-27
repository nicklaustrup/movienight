package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class MovieCreateRequest {
<<<<<<< Updated upstream
//comment
    @NotEmpty
    @JsonProperty("movieId")
    private String movieId;
=======
>>>>>>> Stashed changes

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;
    }

}
