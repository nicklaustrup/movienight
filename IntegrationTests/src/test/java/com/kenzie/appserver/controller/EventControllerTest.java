package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.RSVP;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    EventService eventService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        String eventId = UUID.randomUUID().toString();
        String eventTitle = mockNeat.strings().valStr();
        String movieId = mockNeat.strings().valStr();
        LocalDateTime date = LocalDateTime.now();
        Boolean active = true;

        List<RSVP> users = new ArrayList<>();

        Event event = new Event(eventId, eventTitle, movieId, date, active);
        Event persistedEvent = eventService.addNewEvent(event, users);
        mvc.perform(get("/event/{eventId}", persistedEvent.getEventId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("eventId")
                        .value(is(eventId)))
                .andExpect(jsonPath("eventTitle")
                        .value(is(eventTitle)))
                .andExpect(jsonPath("movieId")
                        .value(is(movieId)))
                .andExpect(status().isOk());
    }

//    @Test
//    public void createExample_CreateSuccessful() throws Exception {
//        String name = mockNeat.strings().valStr();
//
//        ExampleCreateRequest exampleCreateRequest = new ExampleCreateRequest();
//        exampleCreateRequest.setName(name);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        mvc.perform(post("/example")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(exampleCreateRequest)))
//                .andExpect(jsonPath("id")
//                        .exists())
//                .andExpect(jsonPath("name")
//                        .value(is(name)))
//                .andExpect(status().isCreated());
//    }
}
