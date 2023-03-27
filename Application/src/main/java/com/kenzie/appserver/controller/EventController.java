package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.service.EventService;

import com.kenzie.appserver.service.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(@PathVariable("eventId") String eventId) {

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
        eventResponse.setUsers(event.getUsers());
        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        Event event = new Event(randomUUID().toString(),
                eventCreateRequest.getEventTitle(),
                eventCreateRequest.getMovieId(),
                eventCreateRequest.getDate(),
                eventCreateRequest.getActive(),
                eventCreateRequest.getUsers()
        );
        eventService.addNewEvent(event);

        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setEventTitle(event.getEventTitle());
        eventResponse.setMovieId(event.getMovieId());
        eventResponse.setDate(event.getDate());
        eventResponse.setActive(event.getActive());
        eventResponse.setUsers(event.getUsers());

        return ResponseEntity.created(URI.create("/event/" + eventResponse.getEventId())).body(eventResponse);
    }
}
