package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.controller.model.RSVPUser;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.EventService;

import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Movie;
import com.kenzie.appserver.service.model.RSVP;
import com.kenzie.appserver.service.model.User;
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
    private MovieService movieService;
    private UserService userService;

    EventController(EventService eventService, RSVPService rsvpService, MovieService movieService, UserService userService) {
        this.eventService = eventService;
        this.rsvpService = rsvpService;
        this.movieService = movieService;
        this.userService = userService;
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

        //get movie details
        Movie movie = movieService.findById(event.getMovieId());
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        eventResponse.setTitle(movie.getTitle());
        eventResponse.setDescription(movie.getDescription());

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<RSVPUser> usersForEvent = new ArrayList<>();
        for (RSVPRecord rsvpRecord:rsvpList) {
            if (rsvpRecord.getEventId().equals(eventId)) {
                User user = userService.findById(rsvpRecord.getUserId());
                if (user == null) {
                    return ResponseEntity.notFound().build();
                }
                RSVPUser rsvpUser = new RSVPUser(rsvpRecord.getEventId(), rsvpRecord.getUserId(),user.getFirstName(), user.getLastName(),rsvpRecord.getIsAttending());
                usersForEvent.add(rsvpUser);
            }
        }
        eventResponse.setUsers(usersForEvent);
        return ResponseEntity.ok(eventResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> getAllEvent() {

        List<EventRecord> eventList = eventService.findAll();

        if (eventList == null) {
            return ResponseEntity.notFound().build();
        }

        List<EventResponse> eventResponseList = new ArrayList<>();
        for (EventRecord eventRecord: eventList) {
            EventResponse eventResponse = new EventResponse();
            eventResponse.setEventId(eventRecord.getEventId());
            eventResponse.setEventTitle(eventRecord.getEventTitle());
            eventResponse.setMovieId(eventRecord.getMovieId());
            eventResponse.setDate(eventRecord.getDate());
            eventResponse.setActive(eventRecord.getActive());
            //get movie details
            Movie movie = movieService.findById(eventRecord.getMovieId());
            if (movie == null) {
                return ResponseEntity.notFound().build();
            }
            eventResponse.setTitle(movie.getTitle());
            eventResponse.setDescription(movie.getDescription());
            eventResponseList.add(eventResponse);
        }

        return ResponseEntity.ok(eventResponseList);
    }
    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        String eventId = randomUUID().toString();
        List<RSVP> users = new ArrayList<>();
        List<RSVPUser> RSVPUsers = new ArrayList<>();
        RSVP userRVSP;
        for (String user: eventCreateRequest.getUsers()) {
            userRVSP = new RSVP(user, eventId, false);
            users.add(userRVSP);
            User userDetails = userService.findById(user);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            RSVPUsers.add(new RSVPUser(eventId, user, userDetails.getFirstName(), userDetails.getLastName(), false));
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

        //get movie and user details
        Movie movie = movieService.findById(event.getMovieId());
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        eventResponse.setTitle(movie.getTitle());
        eventResponse.setDescription(movie.getDescription());
        eventResponse.setUsers(RSVPUsers);

        return ResponseEntity.created(URI.create("/event/" + eventResponse.getEventId())).body(eventResponse);
    }
    @PutMapping
    public ResponseEntity<EventResponse> updateEvent(@RequestBody EventUpdateRequest eventUpdateRequest) {
        Event event = new Event(eventUpdateRequest.getEventId(),
                eventUpdateRequest.getEventTitle(),
                eventUpdateRequest.getMovieId(),
                eventUpdateRequest.getDate(),
                eventUpdateRequest.getActive());
        eventService.updateEvent(event);

        EventResponse eventResponse = createEventResponse(event);

        //get movie and user details
        Movie movie = movieService.findById(event.getMovieId());
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        eventResponse.setTitle(movie.getTitle());
        eventResponse.setDescription(movie.getDescription());

        //create a list of RSVPs
        List<RSVPRecord> rsvpList = rsvpService.findAll();

        //Create a list for all users invited to an event
        List<RSVPUser> usersForEvent = new ArrayList<>();
        for (RSVPRecord rsvpRecord:rsvpList) {
            if (rsvpRecord.getEventId().equals(event.getEventId())) {
                User user = userService.findById(rsvpRecord.getUserId());
                if (user == null) {
                    return ResponseEntity.notFound().build();
                }
                RSVPUser rsvpUser = new RSVPUser(rsvpRecord.getEventId(), rsvpRecord.getUserId(),user.getFirstName(), user.getLastName(),rsvpRecord.getIsAttending());
                usersForEvent.add(rsvpUser);
            }
        }
        eventResponse.setUsers(usersForEvent);

        return ResponseEntity.ok(eventResponse);
    }
    private EventResponse createEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventTitle(event.getEventTitle());
        eventResponse.setMovieId(event.getMovieId());
        eventResponse.setDate(event.getDate());
        eventResponse.setActive(event.getActive());
        return eventResponse;
    }
}
