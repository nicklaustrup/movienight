package com.kenzie.appserver.service.model;

public class RSVP {
    private final String userId;
    private final String eventId;
    private final Boolean isAttending;

    public RSVP(String userId, String eventId, boolean isAttending) {
        this.userId = userId;
        this.eventId = eventId;
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
