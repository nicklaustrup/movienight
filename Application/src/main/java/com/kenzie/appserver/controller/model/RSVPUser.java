package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RSVPUser {
    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("isAttending")
    private Boolean isAttending;

    public RSVPUser () {
    }
    public RSVPUser(String eventId, String userId, String firstName, String lastName, Boolean isAttending) {
        this.eventId = eventId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAttending = isAttending;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getAttending() {
        return isAttending;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAttending(Boolean attending) {
        isAttending = attending;
    }
}
