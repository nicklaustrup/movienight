package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Movie;
import com.kenzie.appserver.service.model.RSVP;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/rsvp")
public class RSVPController {


    private RSVPService rsvpService;
    private UserService userService;
    private EventService eventService;
    private MovieService movieService;

    RSVPController(RSVPService rsvpService, UserService userService, EventService eventService, MovieService movieService) {
        this.rsvpService = rsvpService;
        this.userService = userService;
        this.eventService = eventService;
        this.movieService = movieService;
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

        for (RSVPRecord rsvp : rsvpList) {
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
    public ResponseEntity<List<EventResponse>> getAllEventsForUser(@PathVariable("userId") String userId) {

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<EventResponse> eventResponseList = new ArrayList<>();

        for (RSVPRecord rsvp : rsvpList) {
            if (rsvp.getUserId().equals(userId)) {
                Event event = eventService.findById(rsvp.getEventId());

                EventResponse eventResponse = new EventResponse();
                eventResponse.setEventId(event.getEventId());
                eventResponse.setEventTitle(event.getEventTitle());
                eventResponse.setMovieId(event.getMovieId());
                eventResponse.setDate(event.getDate());
                eventResponse.setActive(event.getActive());

                RSVPCompositeId recordRSVP = new RSVPCompositeId();
                recordRSVP.setUserId(userId);
                recordRSVP.setEventId(rsvp.getEventId());
                RSVP rsvpAttendance = rsvpService.findById(recordRSVP);
                eventResponse.setAttending(rsvpAttendance.getIsAttending());

                //get movie details
                Movie movie = movieService.findById(event.getMovieId());
                if (movie == null) {
                    return ResponseEntity.notFound().build();
                }
                eventResponse.setTitle(movie.getTitle());
                eventResponse.setDescription(movie.getDescription());
                eventResponseList.add(eventResponse);
            }
        }
        return ResponseEntity.ok(eventResponseList);
    }

    @PutMapping
    public ResponseEntity<RSVPResponse> updateRSVP(@RequestBody RSVPCreateRequest rsvpUpdateRequest) {

//        RSVPCompositeId record = new RSVPCompositeId();
//        record.setUserId(rsvpUpdateRequest.getUserId());
//        record.setEventId(rsvpUpdateRequest.getEventId());

//        RSVP rsvp = rsvpService.findById(record);
//
//        if (rsvp == null) {
//            return ResponseEntity.notFound().build();
//        }

        RSVP updatedRSVP = rsvpService.updateRSVP(new RSVP(rsvpUpdateRequest.getUserId(),
                rsvpUpdateRequest.getEventId(),
                rsvpUpdateRequest.getIsAttending()));

        RSVPResponse rsvpResponse = new RSVPResponse();
        rsvpResponse.setUserId(updatedRSVP.getUserId());
        rsvpResponse.setEventId(updatedRSVP.getEventId());
        rsvpResponse.setIsAttending(updatedRSVP.getIsAttending());

        return ResponseEntity.ok(rsvpResponse);
    }
}
