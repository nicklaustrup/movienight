package com.kenzie.appserver.service.model;

import java.time.LocalDateTime;

public class Event {
    private final String eventId;
    private final String eventTitle;
    private final String movieId;
    private final LocalDateTime date;
    private final Boolean active;

    public Event(String eventId, String eventTitle, String movieId, LocalDateTime date, Boolean active) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.movieId = movieId;
        this.date = date;
        this.active = active;
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
}
