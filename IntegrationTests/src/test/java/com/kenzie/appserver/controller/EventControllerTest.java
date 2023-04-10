package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.converter.LocalDateTimeConverter;
import com.kenzie.appserver.repositories.MovieRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.MovieRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.MovieService;
import com.kenzie.appserver.service.RSVPService;

import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Movie;
import com.kenzie.appserver.service.model.RSVP;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.kenzie.appserver.service.model.User;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private QueryUtility queryUtility;

    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mvc);
    }

    @Test
    public void can_create_user() throws Exception {
        // GIVEN
        String firstName = mockNeat.strings().get();
        String lastName = mockNeat.strings().get();
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFirstName(firstName);
        userCreateRequest.setLastName(lastName);

        //WHEN
        queryUtility.userControllerClient.createUser(userCreateRequest)
                //THEN
                .andExpect(status().isCreated());
    }

    @Test
    public void can_create_movie() throws Exception {
        // GIVEN
        String title = mockNeat.strings().get();
        String description = mockNeat.strings().get();
        MovieCreateRequest movieCreateRequest = new MovieCreateRequest();
        movieCreateRequest.setTitle(title);
        movieCreateRequest.setDescription(description);

        //WHEN
        queryUtility.movieControllerClient.createMovie(movieCreateRequest)
                //THEN
                .andExpect(status().isCreated());
    }

    @Test
    public void can_create_event() throws Exception {

        mapper.registerModule(new JavaTimeModule());
        LocalDateTimeConverter dateTimeConverter = new LocalDateTimeConverter();

        // GIVEN
        String firstName = mockNeat.strings().get();
        String lastName = mockNeat.strings().get();
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFirstName(firstName);
        userCreateRequest.setLastName(lastName);

        //Create User
        ResultActions userResultActions = queryUtility.userControllerClient.createUser(userCreateRequest)
                .andExpect(status().isCreated());

        MvcResult userResult = userResultActions.andReturn();
        String userContentAsString = userResult.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(userContentAsString, UserResponse.class);
        String userId = userResponse.getUserId();

        String title = mockNeat.strings().get();
        String description = mockNeat.strings().get();
        MovieCreateRequest movieCreateRequest = new MovieCreateRequest();
        movieCreateRequest.setTitle(title);
        movieCreateRequest.setDescription(description);

        ResultActions movieResultAction = queryUtility.movieControllerClient.createMovie(movieCreateRequest)
                .andExpect(status().isCreated());

        MvcResult movieResult = movieResultAction.andReturn();
        String movieContentAsString = movieResult.getResponse().getContentAsString();
        MovieResponse movieResponse = mapper.readValue(movieContentAsString, MovieResponse.class);
        String movieId = movieResponse.getMovieId();

        String evenTitle = mockNeat.strings().get();
        LocalDateTime date = dateTimeConverter.unconvert("2023-04-29T17:24");
        List<String> users = new ArrayList<>();
        users.add(userId);

        EventCreateRequest eventCreateRequest = new EventCreateRequest();
        eventCreateRequest.setEventTitle(evenTitle);
        eventCreateRequest.setMovieId(movieId);
        eventCreateRequest.setDate(date);
        eventCreateRequest.setActive(true);
        eventCreateRequest.setUsers(users);

        queryUtility.eventControllerClient.createEvent(eventCreateRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("eventTitle")
                        .value(is(eventCreateRequest.getEventTitle())))
                .andExpect(jsonPath("movieId")
                        .value(is(eventCreateRequest.getMovieId())))
//                .andExpect(jsonPath("date")
//                        .value(is(dateTimeConverter.convert(eventCreateRequest.getDate()))))
                .andExpect(jsonPath("active")
                        .value(is(eventCreateRequest.getActive())))
                .andExpect(jsonPath("users[0].userId")
                        .value(is(eventCreateRequest.getUsers().get(0))))
                .andExpect(jsonPath("users[0].firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("users[0].lastName")
                        .value(is(lastName)));
    }

    @Test
    public void can_get_event() throws Exception {

        mapper.registerModule(new JavaTimeModule());
        LocalDateTimeConverter dateTimeConverter = new LocalDateTimeConverter();

        // GIVEN
        String firstName = mockNeat.strings().get();
        String lastName = mockNeat.strings().get();
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFirstName(firstName);
        userCreateRequest.setLastName(lastName);

        //Create User
        ResultActions userResultActions = queryUtility.userControllerClient.createUser(userCreateRequest)
                .andExpect(status().isCreated());

        MvcResult userResult = userResultActions.andReturn();
        String userContentAsString = userResult.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(userContentAsString, UserResponse.class);
        String userId = userResponse.getUserId();

        String title = mockNeat.strings().get();
        String description = mockNeat.strings().get();
        MovieCreateRequest movieCreateRequest = new MovieCreateRequest();
        movieCreateRequest.setTitle(title);
        movieCreateRequest.setDescription(description);

        ResultActions movieResultAction = queryUtility.movieControllerClient.createMovie(movieCreateRequest)
                .andExpect(status().isCreated());

        MvcResult movieResult = movieResultAction.andReturn();
        String movieContentAsString = movieResult.getResponse().getContentAsString();
        MovieResponse movieResponse = mapper.readValue(movieContentAsString, MovieResponse.class);
        String movieId = movieResponse.getMovieId();

        String evenTitle = mockNeat.strings().get();
        LocalDateTime date = dateTimeConverter.unconvert("2023-04-29T17:24");
        List<String> users = new ArrayList<>();
        users.add(userId);

        EventCreateRequest eventCreateRequest = new EventCreateRequest();
        eventCreateRequest.setEventTitle(evenTitle);
        eventCreateRequest.setMovieId(movieId);
        eventCreateRequest.setDate(date);
        eventCreateRequest.setActive(true);
        eventCreateRequest.setUsers(users);

        ResultActions eventResultAction = queryUtility.eventControllerClient.createEvent(eventCreateRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("eventTitle")
                        .value(is(eventCreateRequest.getEventTitle())))
                .andExpect(jsonPath("movieId")
                        .value(is(eventCreateRequest.getMovieId())))
//                .andExpect(jsonPath("date")
//                        .value(is(dateTimeConverter.convert(eventCreateRequest.getDate()))))
                .andExpect(jsonPath("active")
                        .value(is(eventCreateRequest.getActive())))
                .andExpect(jsonPath("users[0].userId")
                        .value(is(eventCreateRequest.getUsers().get(0))))
                .andExpect(jsonPath("users[0].firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("users[0].lastName")
                        .value(is(lastName)));

        MvcResult eventResult = eventResultAction.andReturn();
        String eventContentAsString = eventResult.getResponse().getContentAsString();
        EventResponse eventResponse = mapper.readValue(eventContentAsString, EventResponse.class);
        String eventId = eventResponse.getEventId();

        queryUtility.eventControllerClient.getEvent(eventId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("eventTitle")
                        .value(is(eventCreateRequest.getEventTitle())))
                .andExpect(jsonPath("movieId")
                        .value(is(eventCreateRequest.getMovieId())))
//                .andExpect(jsonPath("date")
//                        .value(is(dateTimeConverter.convert(eventCreateRequest.getDate()))))
                .andExpect(jsonPath("active")
                        .value(is(eventCreateRequest.getActive())))
                .andExpect(jsonPath("users[0].userId")
                        .value(is(eventCreateRequest.getUsers().get(0))))
                .andExpect(jsonPath("users[0].firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("users[0].lastName")
                        .value(is(lastName)));
    }

    @Test
    public void can_update_event() throws Exception {

        mapper.registerModule(new JavaTimeModule());
        LocalDateTimeConverter dateTimeConverter = new LocalDateTimeConverter();

        // GIVEN
        String firstName = mockNeat.strings().get();
        String lastName = mockNeat.strings().get();
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFirstName(firstName);
        userCreateRequest.setLastName(lastName);

        //Create User
        ResultActions userResultActions = queryUtility.userControllerClient.createUser(userCreateRequest)
                .andExpect(status().isCreated());

        MvcResult userResult = userResultActions.andReturn();
        String userContentAsString = userResult.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(userContentAsString, UserResponse.class);
        String userId = userResponse.getUserId();

        String title = mockNeat.strings().get();
        String description = mockNeat.strings().get();
        MovieCreateRequest movieCreateRequest = new MovieCreateRequest();
        movieCreateRequest.setTitle(title);
        movieCreateRequest.setDescription(description);

        ResultActions movieResultAction = queryUtility.movieControllerClient.createMovie(movieCreateRequest)
                .andExpect(status().isCreated());

        MvcResult movieResult = movieResultAction.andReturn();
        String movieContentAsString = movieResult.getResponse().getContentAsString();
        MovieResponse movieResponse = mapper.readValue(movieContentAsString, MovieResponse.class);
        String movieId = movieResponse.getMovieId();

        String evenTitle = mockNeat.strings().get();
        LocalDateTime date = dateTimeConverter.unconvert("2023-04-29T17:24");
        List<String> users = new ArrayList<>();
        users.add(userId);

        EventCreateRequest eventCreateRequest = new EventCreateRequest();
        eventCreateRequest.setEventTitle(evenTitle);
        eventCreateRequest.setMovieId(movieId);
        eventCreateRequest.setDate(date);
        eventCreateRequest.setActive(true);
        eventCreateRequest.setUsers(users);

        ResultActions eventResultAction = queryUtility.eventControllerClient.createEvent(eventCreateRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("eventTitle")
                        .value(is(eventCreateRequest.getEventTitle())))
                .andExpect(jsonPath("movieId")
                        .value(is(eventCreateRequest.getMovieId())))
//                .andExpect(jsonPath("date")
//                        .value(is(dateTimeConverter.convert(eventCreateRequest.getDate()))))
                .andExpect(jsonPath("active")
                        .value(is(eventCreateRequest.getActive())))
                .andExpect(jsonPath("users[0].userId")
                        .value(is(eventCreateRequest.getUsers().get(0))))
                .andExpect(jsonPath("users[0].firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("users[0].lastName")
                        .value(is(lastName)));

        MvcResult eventResult = eventResultAction.andReturn();
        String eventContentAsString = eventResult.getResponse().getContentAsString();
        EventResponse eventResponse = mapper.readValue(eventContentAsString, EventResponse.class);
        String eventId = eventResponse.getEventId();

        EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
        eventUpdateRequest.setEventId(eventId);
        eventUpdateRequest.setEventTitle("New title");
        eventUpdateRequest.setMovieId(movieId);
        eventUpdateRequest.setDate(date);
        eventUpdateRequest.setActive(false);

        queryUtility.eventControllerClient.updateEvent(eventUpdateRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("eventTitle")
                        .value(is("New title")))
                .andExpect(jsonPath("active")
                        .value(is(false)));
    }

}
