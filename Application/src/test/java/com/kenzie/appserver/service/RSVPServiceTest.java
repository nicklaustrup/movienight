package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.model.RSVP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RSVPServiceTest {
    private RSVPRepository rsvpRepository;
    private RSVPService rsvpService;

    @BeforeEach
    void setup() {
        rsvpRepository = mock(RSVPRepository.class);
        rsvpService = new RSVPService(rsvpRepository);
    }

    @Test
    void findById() {
        // GIVEN
        String id1 = randomUUID().toString();
        String id2 = randomUUID().toString();

        RSVPCompositeId id = new RSVPCompositeId();
        id.setUserId(id1);
        id.setEventId(id2);

        RSVPRecord rsvpRecord = new RSVPRecord();
        rsvpRecord.setUserId(id1);
        rsvpRecord.setEventId(id2);
        rsvpRecord.setIsAttending(false);

        // WHEN
        when(rsvpRepository.findById(id)).thenReturn(Optional.of(rsvpRecord));
        RSVP rsvp = rsvpService.findById(id);

        // THEN
        Assertions.assertNotNull(rsvp, "The rsvp is returned");
        assertEquals(rsvpRecord.getUserId(), rsvp.getUserId(), "The user id matches");
        assertEquals(rsvpRecord.getEventId(), rsvp.getEventId(), "The event id matches");
        assertEquals(rsvpRecord.getIsAttending(), rsvp.getIsAttending(), "The attending status matches");
    }

    @Test
    void addNewRSVP() {
        // GIVEN
        String id1 = randomUUID().toString();
        String id2 = randomUUID().toString();

        RSVP rsvp = new RSVP(id1, id2, true);

        RSVPRecord rsvpRecord = new RSVPRecord();
        rsvpRecord.setUserId(id1);
        rsvpRecord.setEventId(id2);
        rsvpRecord.setIsAttending(true);

        // WHEN
        when(rsvpRepository.save(rsvpRecord)).thenReturn(rsvpRecord);

        RSVP result = rsvpService.addNewRSVP(rsvp);

        //THEN
        assertEquals(id1, rsvpRecord.getUserId());
        assertEquals(id2, rsvpRecord.getEventId());
        assertEquals(true, rsvpRecord.getIsAttending());

        Assertions.assertSame(rsvp, result);
    }

    @Test
    public void findAllRSVPs() {
        // GIVEN
        List<RSVPRecord> rsvpRecordList = new ArrayList<>();
        RSVPRecord rsvpRecord1 = new RSVPRecord();
        rsvpRecord1.setUserId("1");
        rsvpRecord1.setEventId("2");
        rsvpRecord1.setIsAttending(true);
        rsvpRecordList.add(rsvpRecord1);

        RSVPRecord rsvpRecord2 = new RSVPRecord();
        rsvpRecord2.setUserId("2");
        rsvpRecord2.setEventId("1");
        rsvpRecord2.setIsAttending(false);
        rsvpRecordList.add(rsvpRecord2);

        //WHEN
        when(rsvpRepository.findAll()).thenReturn(rsvpRecordList);


        List<RSVPRecord> allRSVPs = rsvpService.findAll();

        //THEN
        assertEquals(rsvpRecordList.size(), allRSVPs.size());
        Assertions.assertTrue(allRSVPs.contains(rsvpRecord1));
        Assertions.assertTrue(allRSVPs.contains(rsvpRecord2));
        }
    }
