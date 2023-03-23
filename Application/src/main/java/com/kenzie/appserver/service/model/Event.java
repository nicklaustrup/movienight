package com.kenzie.appserver.service.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String eventId;
    private final String eventTitle;
    private final String movieId;
    private final LocalDateTime date;
    private final Boolean active;
    private final List<RSVP> users;

    public Event(String eventId, String eventTitle, String movieId, LocalDateTime date, Boolean active, List<RSVP> users) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.movieId = movieId;
        this.date = date;
        this.active = active;
        this.users = users;//TODO Check if this should be a parameter for the constructor
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Boolean getActive() {
        return active;
    }

    public List<RSVP> getUsers() {
        return users;
    }
}
