package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.RSVP;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public class EventCreateRequest {

    @NotEmpty
    @JsonProperty("eventTitle")
    private String eventTitle;

    @NotEmpty
    @JsonProperty("movieId")
    private String movieId;

    @NotEmpty
    @JsonProperty("date")
    private LocalDateTime date;

    @NotEmpty
    @JsonProperty("active")
    private Boolean active;

    @NotEmpty
    @JsonProperty("users")
    private List<RSVP> users;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<RSVP> getUsers() {
        return users;
    }

    public void setUsers(List<RSVP> users) {
        this.users = users;
    }
}
