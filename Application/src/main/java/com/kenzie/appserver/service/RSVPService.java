package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.stereotype.Service;

@Service
public class RSVPService {
    private RSVPRepository rsvpRepository;

    public RSVPService(RSVPRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }

    //TODO determine what the Hashkey look up is for RSVP records
    public RSVP findByEventId(String eventId) {
        RSVP rsvpFromBackend = rsvpRepository
                .findById(eventId)
                .map(rsvp -> new RSVP(rsvp.getUserId(), rsvp.getEventId(), rsvp.getIsAttending()))
                .orElse(null);

        return rsvpFromBackend;
    }

    public RSVP addNewRSVP(RSVP rsvp) {
        RSVPRecord rsvpRecord = new RSVPRecord();
        rsvpRecord.setUserId(rsvp.getUserId());
        rsvpRecord.setEventId(rsvp.getEventId());
        rsvpRecord.setIsAttending(rsvp.getIsAttending());
        rsvpRepository.save(rsvpRecord);
        return rsvp;
    }
}
