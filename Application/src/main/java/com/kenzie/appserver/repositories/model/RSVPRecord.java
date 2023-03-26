package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;


import java.util.Objects;

@DynamoDBTable(tableName = "RSVP")
public class RSVPRecord {

//    private String userId;
//    private String eventId;

    @Id
    private RSVPCompositeId compositeId;
    private Boolean isAttending;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return compositeId != null ? compositeId.getUserId() : null;
    }

    @DynamoDBRangeKey(attributeName = "eventId")
    public String getEventId() {
        return compositeId != null ? compositeId.getEventId() : null;
    }

    @DynamoDBAttribute(attributeName = "isAttending")
    public Boolean getIsAttending(){ return isAttending;}

    public void setUserId(String userId) {
        if (compositeId == null){
            compositeId = new RSVPCompositeId();
        }
        compositeId.setUserId(userId);
    }

    public void setEventId(String eventId) {
        if (compositeId == null){
            compositeId = new RSVPCompositeId();
        }
        compositeId.setEventId(eventId);
    }

    public void setIsAttending(Boolean isAttending){this.isAttending = isAttending;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RSVPRecord)) return false;
        RSVPRecord that = (RSVPRecord) o;
        return Objects.equals(compositeId, that.compositeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeId);
    }
}
