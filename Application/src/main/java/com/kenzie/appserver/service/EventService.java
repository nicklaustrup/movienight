package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RSVPResponse;
import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;
    private RSVPRepository rsvpRepository;

    public EventService(EventRepository eventRepository, RSVPRepository rsvpRepository) {
        this.eventRepository = eventRepository;
        this.rsvpRepository = rsvpRepository;
    }
    public Event findById(String eventId) {
        Event eventFromBackend = eventRepository
                .findById(eventId)
                .map(event -> new Event(event.getEventId(), event.getEventTitle(), event.getMovieId(), event.getDate(), event.getActive()))
                .orElse(null);

        List<RSVPRecord> rsvpFromBackend = (List<RSVPRecord>) rsvpRepository
                .findAll();

        //Create a list for all users invited to an event
        List<RSVPResponse> usersForEventResponse = new ArrayList<>();

        for (RSVPRecord rsvp:rsvpFromBackend) {
            if (rsvp.getEventId().equals(eventId)) {
                RSVPResponse rsvpResponse = new RSVPResponse();
                rsvpResponse.setUserId(rsvp.getUserId());
                rsvpResponse.setEventId(rsvp.getEventId());
                rsvpResponse.setIsAttending(rsvp.getIsAttending());
                usersForEventResponse.add(rsvpResponse);
            }
        }

        return eventFromBackend;
    }
    public Event addNewEvent(Event event, List<RSVP> users) {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getEventId());
        eventRecord.setEventTitle(event.getEventTitle());
        eventRecord.setMovieId(event.getMovieId());
        eventRecord.setDate(event.getDate());
        eventRecord.setActive(event.getActive());
        eventRepository.save(eventRecord);
        RSVPRecord rsvpRecord;
        for(RSVP user: users) {
             rsvpRecord = new RSVPRecord();
             rsvpRecord.setUserId(user.getUserId());
             rsvpRecord.setEventId(user.getEventId());
             rsvpRecord.setIsAttending(user.getIsAttending());
             rsvpRepository.save(rsvpRecord);
        }
        return event;
    }
    public void updateEvent(Event event) {
        if (eventRepository.existsById(event.getEventId())) {
            EventRecord eventRecord = new EventRecord();
            eventRecord.setEventId(event.getEventId());
            eventRecord.setEventTitle(event.getEventTitle());
            eventRecord.setMovieId(event.getMovieId());
            eventRecord.setDate(event.getDate());
            eventRecord.setActive(event.getActive());
            eventRepository.save(eventRecord);
        }
    }
    public List<EventRecord> findAll() {
        List<EventRecord> eventFromBackend = (List<EventRecord>) eventRepository
                .findAll();

        return eventFromBackend;
    }
}
