package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.RSVP;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Event")
public class EventRecord {
    private String eventId;
    private String eventTitle;
    private String movieId;
    private LocalDateTime date;
    private Boolean active;
    private List<RSVP> users;

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
    public LocalDateTime getDate() {
        return date;
    }

    @DynamoDBAttribute(attributeName = "active")
    public Boolean getActive() {
        return active;
    }

    @DynamoDBAttribute(attributeName = "users")
    public List<RSVP> getUsers() {
        return users;
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

    public void setUsers(List<RSVP> users) {
        this.users = copyUserList(users);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRecord that = (EventRecord) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(eventTitle, that.eventTitle) && Objects.equals(movieId, that.movieId) && Objects.equals(date, that.date) && Objects.equals(active, that.active) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventTitle, movieId, date, active, users);
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
