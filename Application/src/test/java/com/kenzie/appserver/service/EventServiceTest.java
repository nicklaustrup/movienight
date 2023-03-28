package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.RSVP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTest {

    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }
    /** ------------------------------------------------------------------------
     *  eventService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String eventId = randomUUID().toString();
        EventRecord record = new EventRecord();
        record.setEventId(eventId);
        record.setEventTitle("title");
        record.setMovieId("movieId");
        record.setDate(LocalDateTime.now());
        record.setActive(true);

        // WHEN
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(record));
        Event event = eventService.findById(eventId);

        // THEN
        Assertions.assertNotNull(event, "The object is returned");
        Assertions.assertEquals(record.getEventId(), event.getEventId(), "The eventId value matches");
        Assertions.assertEquals(record.getEventTitle(), event.getEventTitle(), "The eventTitle value matches");
        Assertions.assertEquals(record.getMovieId(), event.getMovieId(), "The movieId value matches");
        Assertions.assertEquals(record.getDate(), event.getDate(), "The date value matches");
        Assertions.assertEquals(record.getActive(), event.getActive(), "The active value matches");
    }

}
