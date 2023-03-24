package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RSVPResponse;
import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.model.RSVP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rsvp")
public class RSVPController {

    private RSVPService rsvpService;

    RSVPController(RSVPService rsvpService) {
        this.rsvpService = rsvpService;
    }

    @GetMapping("/{userId}_{eventId}")
    public ResponseEntity<RSVPResponse> get(@PathVariable("userId") String userId,
                                            @PathVariable("eventId") String eventId) {

        RSVPCompositeId record = new RSVPCompositeId();
        record.setUserId(userId);
        record.setEventId(eventId);
        RSVP rsvp = rsvpService.findById(record);
        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }

        RSVPResponse rsvpResponse = new RSVPResponse();
        rsvpResponse.setUserId(rsvp.getUserId());
        rsvpResponse.setEventId(rsvp.getEventId());
        rsvpResponse.setIsAttending(rsvp.getIsAttending());
        return ResponseEntity.ok(rsvpResponse);
    }

//    @PostMapping
//    public ResponseEntity<RSVPResponse> addNewRSVP(@RequestBody RSVPCreateRequest rsvpCreateRequest) {
//        RSVP rsvp = new RSVP(rsvpCreateRequest.getUserId(),
//                rsvpCreateRequest.getEventId(),
//                rsvpCreateRequest.getIsAttending());
//        rsvpService.addNewRSVP(rsvp);
//
//        RSVPResponse rsvpResponse = new RSVPResponse();
//        rsvpResponse.setUserId(rsvp.getUserId());
//        rsvpResponse.setEventId(rsvp.getEventId());
//        rsvpResponse.setIsAttending(rsvp.getIsAttending());
//
//        return ResponseEntity.created(URI.create("/rsvp/" + rsvpResponse.getEventId())).body(rsvpResponse);
//    }
}
