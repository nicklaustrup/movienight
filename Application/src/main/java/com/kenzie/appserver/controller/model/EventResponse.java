package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.RSVP;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("eventTitle")
    private String eventTitle;

    @JsonProperty("movieId")
    private String movieId;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("users")
    private List<RSVP> users;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

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
        this.users = copyUserList(users);
    }

    public List<RSVP> copyUserList (List<RSVP> users) {
        List<RSVP> copyUserList = new ArrayList<>();
        RSVP copyUser;
        for (RSVP user: users) {
            copyUser = new RSVP(user.getUserId(), user.getEventId(), user.getIsAttending());
            copyUserList.add(copyUser);
        }
        return copyUserList;
    }
}
