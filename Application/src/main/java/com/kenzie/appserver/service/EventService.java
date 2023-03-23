package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.service.model.Event;

public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public Event findById(String id) {
        Event eventFromBackend = eventRepository
                .findById(id)
                .map(event -> new Event(event.getEventId(), event.getEventTitle(), event.getMovieId(), event.getDate(), event.getActive(), event.getUsers()))
                .orElse(null);
        return eventFromBackend;
    }
    public Event addNewEvent(Event event) {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getEventId());
        eventRecord.setEventTitle(event.getEventTitle());
        eventRecord.setMovieId(event.getMovieId());
        eventRecord.setDate(event.getDate());
        eventRecord.setActive(event.getActive());
        eventRecord.setUsers(event.getUsers());
        eventRepository.save(eventRecord); //TODO need to add logic for adding RSVP records here as well
        return event;
    }

    //TODO add all the other methods for supporting the other APIs
}
