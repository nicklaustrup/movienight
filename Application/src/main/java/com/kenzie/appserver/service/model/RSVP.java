package com.kenzie.appserver.service.model;

public class RSVP {
    private final String userId;
    private final String eventId;
    private final Boolean isAttending;

    public RSVP(String userId, String name, boolean isAttending) {
        this.userId = userId;
        this.eventId = name;
        this.isAttending = isAttending;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public Boolean getIsAttending(){return isAttending;}
}
