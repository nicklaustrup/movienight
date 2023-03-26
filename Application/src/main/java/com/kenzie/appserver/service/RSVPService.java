package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.stereotype.Service;

@Service
public class RSVPService {

    private RSVPRepository rsvpRepository;

    public RSVPService(RSVPRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }


    public RSVP findById(RSVPCompositeId compositeId) {
        RSVP rsvpFromBackend = null;
                rsvpRepository
                .findById(compositeId)
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
