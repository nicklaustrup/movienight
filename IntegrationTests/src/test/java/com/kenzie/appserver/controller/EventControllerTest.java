package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.RSVP;
import com.kenzie.appserver.service.model.User;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
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
    @Autowired
    UserService userService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

//    @Test
//    public void getById_Exists() throws Exception {
//        String eventId = UUID.randomUUID().toString();
//        String eventTitle = mockNeat.strings().valStr();
//        String movieId = mockNeat.strings().valStr();
//        LocalDateTime date = LocalDateTime.now();
//        Boolean active = true;
//
//        List<RSVP> users = new ArrayList<>();
//
//        Event event = new Event(eventId, eventTitle, movieId, date, active);
//
//        Event persistedEvent = eventService.addNewEvent(event, users);
//
//        mvc.perform(get("/event/{eventId}", persistedEvent.getEventId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("eventId")
//                        .value(is(eventId)))
//                .andExpect(jsonPath("eventTitle")
//                        .value(is(eventTitle)))
//                .andExpect(jsonPath("movieId")
//                        .value(is(movieId)))
//                .andExpect(jsonPath("$.users", hasSize(0)));
//    }

//    @Test
//    public void createEvent_CreateSuccessful() throws Exception {
//        // create mock data for request body
//        EventCreateRequest eventCreateRequest = new EventCreateRequest();
//        eventCreateRequest.setEventTitle("Test Event");
//        eventCreateRequest.setMovieId("1234");
//        eventCreateRequest.setDate(LocalDateTime.now());
//        eventCreateRequest.setActive(true);
//
//        String firstName = mockNeat.strings().valStr();
//        String lastName = mockNeat.strings().valStr();
//
//        User user = new User("user1", firstName, lastName);
//        User persistedUser = userService.addNewUser(user);
//
//        List<String> users = Arrays.asList("user1");
//        eventCreateRequest.setUsers(users);
//
//        mapper.registerModule(new JavaTimeModule());
//
//
//        // perform request and verify response
//        mvc.perform(post("/event")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(eventCreateRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.eventId").exists())
//                .andExpect(jsonPath("$.eventTitle")
//                        .value(is("Test Event")))
//                .andExpect(jsonPath("$.movieId")
//                        .value(is("1234")))
//                .andExpect(jsonPath("$.active")
//                        .value(is(true)))
//                .andExpect(jsonPath("$.users",
//                        hasSize(users.size())))
//                .andExpect(jsonPath("$.users[0].userId")
//                        .value(is("user1")))
//                .andExpect(jsonPath("$.users[0].eventId")
//                        .value(is("Test Event")));
//    }
}
