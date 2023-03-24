package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

public class RSVPCompositeId implements Serializable {

    private String userId;

    private String eventId;

//    public RSVPCompositeId(){}
//
//    public RSVPCompositeId(String userId, String eventId){
//        this.eventId = eventId;
//        this.userId = userId;
//    }

    @DynamoDBHashKey
    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    @DynamoDBRangeKey
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
