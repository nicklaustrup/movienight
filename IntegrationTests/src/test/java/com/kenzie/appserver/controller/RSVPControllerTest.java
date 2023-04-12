package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.MovieCreateRequest;
import com.kenzie.appserver.controller.model.MovieResponse;
import com.kenzie.appserver.controller.model.RSVPCreateRequest;
import com.kenzie.appserver.controller.model.RSVPResponse;
import com.kenzie.appserver.repositories.RSVPRepository;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.RSVPService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Movie;
import com.kenzie.appserver.service.model.RSVP;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@IntegrationTest
class RSVPControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RSVPService rsvpService;
    @Autowired
    EventService eventService;
    @Autowired
    RSVPRepository rsvpRepository;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        String userId = UUID.randomUUID().toString();
        String eventId = UUID.randomUUID().toString();
        boolean isAttending = true;
        RSVP rsvp = new RSVP(userId, eventId, isAttending);
        RSVP persistedRSVP = rsvpService.addNewRSVP(rsvp);

        mvc.perform(get("/rsvp/{userId}_{eventId}", persistedRSVP.getUserId(), persistedRSVP.getEventId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId")
                        .value(is(userId)))
                .andExpect(jsonPath("eventId")
                        .value(is(eventId)))
                .andExpect(jsonPath("isAttending")
                        .value(is(isAttending)))
                .andExpect(status().isOk());
    }

    @Test
    public void createRSVP_CreateSuccessful() throws Exception {
        String userId = UUID.randomUUID().toString();
        String eventId = UUID.randomUUID().toString();

        RSVPCreateRequest rsvpCreateRequest = new RSVPCreateRequest();
        rsvpCreateRequest.setUserId(userId);
        rsvpCreateRequest.setEventId(eventId);
        rsvpCreateRequest.setIsAttending(true);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/rsvp")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rsvpCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("eventId")
                        .value(is(eventId)))
                .andExpect(jsonPath("userId")
                        .value(is(userId)))
                .andExpect(jsonPath("isAttending")
                        .value(is(true)))
                .andExpect(status().isCreated());
    }
//    @Test
//    public void getAllEventsForUser() throws Exception {
//        String userId = UUID.randomUUID().toString();
//        String eventId1 = UUID.randomUUID().toString();
//        String eventId2 = UUID.randomUUID().toString();
//
//        RSVP rsvp1 = new RSVP(userId, eventId1, true);
//        RSVP rsvp2 = new RSVP(userId, eventId2, false);
//
//        rsvpService.addNewRSVP(rsvp1);
//        rsvpService.addNewRSVP(rsvp2);
//
//        // create the events
//        Event event1 = new Event(eventId1, "Event 1", UUID.randomUUID().toString(), LocalDateTime.now(), true);
//        Event event2 = new Event(eventId2, "Event 2", UUID.randomUUID().toString(), LocalDateTime.now(), false);
//
//        List<RSVP> users = new ArrayList<>();
//        users.add(rsvp1);
//        users.add(rsvp2);
//
//        eventService.addNewEvent(event1, Collections.emptyList());
//        eventService.addNewEvent(event2, Collections.emptyList());
//
//        mvc.perform(get("/rsvp/events/{userId}", userId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].eventId").value(event1.getEventId()))
//                .andExpect(jsonPath("$[0].eventTitle").value(event1.getEventTitle()))
//                .andExpect(jsonPath("$[0].date").value(event1.getDate().toString()))
//                .andExpect(jsonPath("$[0].active").value(event1.getActive()))
//                .andExpect(jsonPath("$[0].isAttending").value(rsvp1.getIsAttending()))
//                .andExpect(jsonPath("$[1].eventId").value(event2.getEventId()))
//                .andExpect(jsonPath("$[1].eventTitle").value(event2.getEventTitle()))
//                .andExpect(jsonPath("$[1].date").value(event2.getDate().toString()))
//                .andExpect(jsonPath("$[1].active").value(event2.getActive()))
//                .andExpect(jsonPath("$[1].isAttending").value(rsvp2.getIsAttending()))
//                .andExpect(status().isOk());
//    }
@Test
public void updateRSVP_IsSuccessful() throws Exception {
    String userId = UUID.randomUUID().toString();
    String eventId = UUID.randomUUID().toString();

    RSVPRecord rsvpRecord = new RSVPRecord();
    rsvpRecord.setUserId(userId);
    rsvpRecord.setEventId(eventId);
    rsvpRecord.setIsAttending(true);

    RSVPCreateRequest rsvpUpdateRequest = new RSVPCreateRequest();
    rsvpUpdateRequest.setUserId(userId);
    rsvpUpdateRequest.setEventId(eventId);
    rsvpUpdateRequest.setIsAttending(false);

    mapper.registerModule(new JavaTimeModule());

    mvc.perform(put("/rsvp")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(rsvpUpdateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("eventId")
                    .value(is(eventId)))
            .andExpect(jsonPath("userId")
                    .value(is(userId)))
            .andExpect(jsonPath("isAttending")
                    .value(is(false)));
}
    @Test
    public void getAllUsersForEvent() throws Exception {
        RSVPRecord rsvpRecord1 = new RSVPRecord();
        rsvpRecord1.setUserId("user1");
        rsvpRecord1.setEventId("event1");
        rsvpRecord1.setIsAttending(true);
        rsvpRepository.save(rsvpRecord1);

        RSVPRecord rsvpRecord2 = new RSVPRecord();
        rsvpRecord2.setUserId("user2");
        rsvpRecord2.setEventId("event1");
        rsvpRecord2.setIsAttending(false);
        rsvpRepository.save(rsvpRecord2);

       // RSVP persistedRSVP = rsvpService.addNewRSVP(rsvp);

        String eventId = "event1";
        ResultActions rsvpsResultAction = mvc.perform(get("/rsvp/users/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        MvcResult rsvpsResult = rsvpsResultAction.andReturn();
        String rsvpsContentAsString = rsvpsResult.getResponse().getContentAsString();
        List<RSVPResponse> rsvpsResponse = mapper.readValue(rsvpsContentAsString, new TypeReference<List<RSVPResponse>>(){});
        if (rsvpsResponse.get(0).getUserId().equals(rsvpRecord1.getUserId())) {
            assertEquals(rsvpsResponse.get(0).getUserId(), rsvpRecord1.getUserId(), "The userId matches for Record 1");
            assertEquals(rsvpsResponse.get(0).getEventId(), rsvpRecord1.getEventId(), "The eventId matches for Record 1");
            assertEquals(rsvpsResponse.get(0).getIsAttending(), rsvpRecord1.getIsAttending(), "The isAttending matches for Record 1");
            assertEquals(rsvpsResponse.get(1).getUserId(), rsvpRecord2.getUserId(), "The userId matches for Record 2");
            assertEquals(rsvpsResponse.get(1).getEventId(), rsvpRecord2.getEventId(), "The eventId matches for Record 2");
            assertEquals(rsvpsResponse.get(1).getIsAttending(), rsvpRecord2.getIsAttending(), "The isAttending matches for Record 2");
        }
        else {
            assertEquals(rsvpsResponse.get(0).getUserId(), rsvpRecord2.getUserId(), "The userId matches for Record 2");
            assertEquals(rsvpsResponse.get(0).getEventId(), rsvpRecord2.getEventId(), "The eventId matches for Record 2");
            assertEquals(rsvpsResponse.get(0).getIsAttending(), rsvpRecord2.getIsAttending(), "The isAttending matches for Record 2");
            assertEquals(rsvpsResponse.get(1).getUserId(), rsvpRecord1.getUserId(), "The userId matches for Record 1");
            assertEquals(rsvpsResponse.get(1).getEventId(), rsvpRecord1.getEventId(), "The eventId matches for Record 1");
            assertEquals(rsvpsResponse.get(1).getIsAttending(), rsvpRecord1.getIsAttending(), "The isAttending matches for Record 1");

        }
    }
}