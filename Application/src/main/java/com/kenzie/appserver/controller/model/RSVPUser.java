package com.kenzie.appserver.controller.model;

public class RSVPUser {
    private String eventId;
    private String userId;
    private String firstName;
    private String lastName;
    private Boolean isAttending;

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
}
