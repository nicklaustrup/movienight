package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<String> users;

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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = copyUserList(users);
    }

    public List<String> copyUserList (List<String> users) {
        List<String> copyUserList = new ArrayList<>();
        String copyUser;
        for (String user: users) {
            copyUser = user;
            copyUserList.add(copyUser);
        }
        return copyUserList;
    }
}
