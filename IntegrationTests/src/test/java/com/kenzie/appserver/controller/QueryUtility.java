package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.controller.model.MovieCreateRequest;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class QueryUtility {

    public UserControllerClient userControllerClient;
    public MovieControllerClient movieControllerClient;
    public EventControllerClient eventControllerClient;

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.userControllerClient = new UserControllerClient();
        this.movieControllerClient = new MovieControllerClient();
        this.eventControllerClient = new EventControllerClient();
    }

    public class UserControllerClient {
        public ResultActions getuser(String userId) throws Exception {
            return mvc.perform(get("/user/{userId}", userId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions createUser(UserCreateRequest userCreateRequest) throws Exception {
            return mvc.perform(post("/user/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(userCreateRequest)));
        }
    }

    public class MovieControllerClient {
        public ResultActions getMovie(String movieId) throws Exception {
            return mvc.perform(get("/movie/{movieId}", movieId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions createMovie(MovieCreateRequest movieCreateRequest) throws Exception {
            return mvc.perform(post("/movie/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(movieCreateRequest)));
        }
    }

    public class EventControllerClient {
        public ResultActions getEvent(String eventId) throws Exception {
            return mvc.perform(get("/event/{eventId}", eventId)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions createEvent(EventCreateRequest eventCreateRequest) throws Exception {
            mapper.registerModule(new JavaTimeModule());
            return mvc.perform(post("/event/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(eventCreateRequest)));
        }

        public ResultActions updateEvent(EventUpdateRequest eventUpdateRequest) throws Exception {
            mapper.registerModule(new JavaTimeModule());
            return mvc.perform(put("/event/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(eventUpdateRequest)));
        }
    }
}

