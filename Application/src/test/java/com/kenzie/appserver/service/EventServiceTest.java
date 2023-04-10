package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.RSVP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    private EventRepository eventRepository;
    private EventService eventService;
    private RSVPRepository rsvpRepository;
    private RSVPService rsvpService;


    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        rsvpRepository = mock(RSVPRepository.class);
        eventService = new EventService(eventRepository, rsvpRepository);
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

        RSVPRecord rsvpRecord = new RSVPRecord();
        rsvpRecord.setUserId(randomUUID().toString());
        rsvpRecord.setEventId(eventId);
        rsvpRecord.setIsAttending(true);

        List<RSVPRecord> rsvpFromBackend = new ArrayList<>();
        rsvpFromBackend.add(rsvpRecord);

        // WHEN
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(record));
        when(rsvpRepository.findAll()).thenReturn(rsvpFromBackend);
        Event event = eventService.findById(eventId);

        // THEN
        Assertions.assertNotNull(event, "The object is returned");
        Assertions.assertEquals(record.getEventId(), event.getEventId(), "The eventId value matches");
        Assertions.assertEquals(record.getEventTitle(), event.getEventTitle(), "The eventTitle value matches");
        Assertions.assertEquals(record.getMovieId(), event.getMovieId(), "The movieId value matches");
        Assertions.assertEquals(record.getDate(), event.getDate(), "The date value matches");
        Assertions.assertEquals(record.getActive(), event.getActive(), "The active value matches");
    }
    @Test
    void findAll() {
        // GIVEN
        String eventId = randomUUID().toString();
        EventRecord record = new EventRecord();
        record.setEventId(eventId);
        record.setEventTitle("title");
        record.setMovieId("movieId");
        record.setDate(LocalDateTime.now());
        record.setActive(true);

        List<EventRecord> eventFromBackend = new ArrayList<>();
        eventFromBackend.add(record);


        RSVPRecord rsvpRecord = new RSVPRecord();
        rsvpRecord.setUserId(randomUUID().toString());
        rsvpRecord.setEventId(eventId);
        rsvpRecord.setIsAttending(true);

        List<RSVPRecord> rsvpFromBackend = new ArrayList<>();
        rsvpFromBackend.add(rsvpRecord);

        // WHEN
        when(eventRepository.findAll()).thenReturn(eventFromBackend);
        List<EventRecord> event = eventService.findAll();

        // THEN
        Assertions.assertNotNull(event, "The object is returned");
        Assertions.assertEquals(event.get(0).getEventId(), eventFromBackend.get(0).getEventId(), "The eventId value matches");
        Assertions.assertEquals(event.get(0).getEventTitle(), eventFromBackend.get(0).getEventTitle(), "The eventTitle value matches");
        Assertions.assertEquals(event.get(0).getMovieId(), eventFromBackend.get(0).getMovieId(), "The movieId value matches");
        Assertions.assertEquals(event.get(0).getDate(), eventFromBackend.get(0).getDate(), "The date value matches");
        Assertions.assertEquals(event.get(0).getActive(), eventFromBackend.get(0).getActive(), "The active value matches");
    }
    @Test
    void addNewEvent() {
        // GIVEN
        Event event = new Event("1", "title", "movieId", LocalDateTime.now(), true);
        List<RSVP> users = new ArrayList<>();
        users.add(new RSVP("1", event.getEventId(), true));
        users.add(new RSVP("2", event.getEventId(), false));

        EventRecord expectedEventRecord = new EventRecord();
        expectedEventRecord.setEventId(event.getEventId());
        expectedEventRecord.setEventTitle(event.getEventTitle());
        expectedEventRecord.setMovieId(event.getMovieId());
        expectedEventRecord.setDate(event.getDate());
        expectedEventRecord.setActive(event.getActive());
        when(eventRepository.save(any(EventRecord.class))).thenReturn(expectedEventRecord);

        RSVPRecord expectedRSVPRecord1 = new RSVPRecord();
        expectedRSVPRecord1.setUserId(users.get(0).getUserId());
        expectedRSVPRecord1.setEventId(users.get(0).getEventId());
        expectedRSVPRecord1.setIsAttending(users.get(0).getIsAttending());
        when(rsvpRepository.save(any(RSVPRecord.class))).thenReturn(expectedRSVPRecord1);

        RSVPRecord expectedRSVPRecord2 = new RSVPRecord();
        expectedRSVPRecord2.setUserId(users.get(1).getUserId());
        expectedRSVPRecord2.setEventId(users.get(1).getEventId());
        expectedRSVPRecord2.setIsAttending(users.get(1).getIsAttending());
        when(rsvpRepository.save(any(RSVPRecord.class))).thenReturn(expectedRSVPRecord2);

        // WHEN
        Event result = eventService.addNewEvent(event, users);

        // THEN
        verify(eventRepository, times(1)).save(any(EventRecord.class));
        verify(rsvpRepository, times(2)).save(any(RSVPRecord.class));
        Assertions.assertEquals(expectedEventRecord.getEventId(), result.getEventId());
        Assertions.assertEquals(expectedEventRecord.getEventTitle(), result.getEventTitle());
        Assertions.assertEquals(expectedEventRecord.getMovieId(), result.getMovieId());
        Assertions.assertEquals(expectedEventRecord.getDate(), result.getDate());
        Assertions.assertEquals(expectedEventRecord.getActive(), result.getActive());
    }

    @Test
    void updateEvent() {
        // GIVEN
        String eventId = randomUUID().toString();
        Event event = new Event(eventId, "title", "movieId", LocalDateTime.now(), true);

        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(eventId);
        eventRecord.setEventTitle("Old Title");
        eventRecord.setMovieId("Old Movie");
        eventRecord.setDate(LocalDateTime.now());
        eventRecord.setActive(true);
        when(eventRepository.existsById(eventId)).thenReturn(true);
        ArgumentCaptor<EventRecord> eventRecordArgumentCaptor = ArgumentCaptor.forClass(EventRecord.class);

        // WHEN
        eventService.updateEvent(event);

        // THEN
        verify(eventRepository, times(1)).save(eventRecordArgumentCaptor.capture());
        EventRecord updatedEventRecord = eventRecordArgumentCaptor.getValue();
        Assertions.assertEquals(eventId, updatedEventRecord.getEventId());
        Assertions.assertEquals(event.getEventTitle(), updatedEventRecord.getEventTitle());
        Assertions.assertEquals(event.getMovieId(), updatedEventRecord.getMovieId());
        Assertions.assertEquals(event.getDate(), updatedEventRecord.getDate());
        Assertions.assertEquals(event.getActive(), updatedEventRecord.getActive());
    }

}
