package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kenzie.appserver.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "Event")
public class EventRecord {
    private String eventId;
    private String eventTitle;
    private String movieId;
    private LocalDateTime date;
    private Boolean active;

    @DynamoDBHashKey(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    @DynamoDBAttribute(attributeName = "eventTitle")
    public String getEventTitle() {
        return eventTitle;
    }

    @DynamoDBAttribute(attributeName = "movieId")
    public String getMovieId() {
        return movieId;
    }

    @DynamoDBAttribute(attributeName = "date")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getDate() {
        return date;
    }

    @DynamoDBAttribute(attributeName = "active")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean getActive() {
        return active;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRecord that = (EventRecord) o;
        return eventId.equals(that.eventId) && eventTitle.equals(that.eventTitle) && movieId.equals(that.movieId) && date.equals(that.date) && active.equals(that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventTitle, movieId, date, active);
    }
}
