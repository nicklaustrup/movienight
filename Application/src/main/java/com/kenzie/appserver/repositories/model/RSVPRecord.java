package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "RSVP")
public class RSVPRecord {

    private String userId;
    private String eventId;
    private Boolean isAttending;

    //TODO Decide what the hashkey is
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    @DynamoDBAttribute(attributeName = "isAttending")
    public Boolean getIsAttending(){ return isAttending;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setIsAttending(Boolean isAttending){this.isAttending = isAttending;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RSVPRecord rsvpRecord = (RSVPRecord) o;
        return Objects.equals(userId + eventId, rsvpRecord.userId + rsvpRecord.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId + eventId);
    }
}
