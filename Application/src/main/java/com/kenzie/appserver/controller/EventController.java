package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.EventService;

import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;
    private RSVPService rsvpService;

    EventController(EventService eventService, RSVPService rsvpService) {
        this.eventService = eventService;
        this.rsvpService = rsvpService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("eventId") String eventId) {

        Event event = eventService.findById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventTitle(event.getEventTitle());
        eventResponse.setMovieId(event.getMovieId());
        eventResponse.setDate(event.getDate());
        eventResponse.setActive(event.getActive());

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<RSVP> usersForEvent = new ArrayList<>();

        for (RSVPRecord rsvpRecord:rsvpList) {
            if (rsvpRecord.getEventId().equals(eventId)) {
                RSVP rsvp = new RSVP(rsvpRecord.getUserId(), rsvpRecord.getEventId(),rsvpRecord.getIsAttending());
                usersForEvent.add(rsvp);
            }
        }
        eventResponse.setUsers(usersForEvent);

        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        String eventId = randomUUID().toString();
        List<RSVP> users = new ArrayList<>();
        RSVP userRVSP;
        for (String user: eventCreateRequest.getUsers()) {
            userRVSP = new RSVP(user, eventId, false);
            users.add(userRVSP);
        }
        Event event = new Event(eventId,
                eventCreateRequest.getEventTitle(),
                eventCreateRequest.getMovieId(),
                eventCreateRequest.getDate(),
                eventCreateRequest.getActive()
        );
        eventService.addNewEvent(event,users);

        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventTitle(event.getEventTitle());
        eventResponse.setMovieId(event.getMovieId());
        eventResponse.setDate(event.getDate());
        eventResponse.setActive(event.getActive());
        eventResponse.setUsers(users);

        return ResponseEntity.created(URI.create("/event/" + eventResponse.getEventId())).body(eventResponse);
    }
}
