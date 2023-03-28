package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RSVPCreateRequest;
import com.kenzie.appserver.controller.model.RSVPResponse;
import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/rsvp")
public class RSVPController {

    @Autowired
    private RSVPService rsvpService;

    RSVPController(RSVPService rsvpService) {
        this.rsvpService = rsvpService;
    }

    @GetMapping("/{userId}_{eventId}")
    public ResponseEntity<RSVPResponse> getUsersEvents(@PathVariable("userId") String userId,
                                            @PathVariable("eventId") String eventId) {

        RSVPCompositeId record = new RSVPCompositeId();
        record.setUserId(userId);
        record.setEventId(eventId);

        RSVP rsvp = rsvpService.findById(record);

        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }

        RSVPResponse rsvpResponse = new RSVPResponse();
        rsvpResponse.setUserId(rsvp.getUserId());
        rsvpResponse.setEventId(rsvp.getEventId());
        rsvpResponse.setIsAttending(rsvp.getIsAttending());
        return ResponseEntity.ok(rsvpResponse);
    }

    @PostMapping
    public ResponseEntity<RSVPResponse> addNewRSVP(@RequestBody RSVPCreateRequest rsvpCreateRequest) {
        RSVP rsvp = new RSVP(rsvpCreateRequest.getUserId(),
                rsvpCreateRequest.getEventId(),
                rsvpCreateRequest.getIsAttending());
        rsvpService.addNewRSVP(rsvp);

        RSVPResponse rsvpResponse = new RSVPResponse();
        rsvpResponse.setUserId(rsvp.getUserId());
        rsvpResponse.setEventId(rsvp.getEventId());
        rsvpResponse.setIsAttending(rsvp.getIsAttending());

        return ResponseEntity.created(URI.create("/rsvp/" + rsvpResponse.getEventId())).body(rsvpResponse);
    }

    //Get All Users invited to an event
    @GetMapping("/users/{eventId}")
    public ResponseEntity<List<RSVPResponse>> getAllUsersForEvent(@PathVariable("eventId") String eventId) {

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<RSVPResponse> usersForEventResponse = new ArrayList<>();

        for (RSVPRecord rsvp:rsvpList) {
            if (rsvp.getEventId().equals(eventId)) {
                RSVPResponse rsvpResponse = new RSVPResponse();
                rsvpResponse.setUserId(rsvp.getUserId());
                rsvpResponse.setEventId(rsvp.getEventId());
                rsvpResponse.setIsAttending(rsvp.getIsAttending());
                usersForEventResponse.add(rsvpResponse);
            }
        }

        return ResponseEntity.ok(usersForEventResponse);
    }

    //Get all Events a User is invited to
    @GetMapping("/events/{userId}")
    public ResponseEntity<List<RSVPResponse>> getAllEventsForUser(@PathVariable("userId") String userId) {

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<RSVPResponse> eventsForUserResponse = new ArrayList<>();

        for (RSVPRecord rsvp:rsvpList) {
            if (rsvp.getUserId().equals(userId)) {
                RSVPResponse rsvpResponse = new RSVPResponse();
                rsvpResponse.setUserId(rsvp.getUserId());
                rsvpResponse.setEventId(rsvp.getEventId());
                rsvpResponse.setIsAttending(rsvp.getIsAttending());
                eventsForUserResponse.add(rsvpResponse);
            }
        }

        return ResponseEntity.ok(eventsForUserResponse);
    }
}
