package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;

import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@IntegrationTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;


//    @Autowired
//    ExampleService exampleService;


    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {
        String id = UUID.randomUUID().toString();
        String firstName = mockNeat.strings().valStr();
        String lastName = mockNeat.strings().valStr();

        User user = new User(id, firstName, lastName);
        User persistedUser = userService.addNewUser(user);
        mvc.perform(get("/user/{id}", persistedUser.getUserId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId")
                        .value(is(id)))
                .andExpect(jsonPath("firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("lastName")
                        .value(is(lastName)))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_CreateSuccessful() throws Exception {
        String firstName = mockNeat.strings().valStr();
        String lastName = mockNeat.strings().valStr();

        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFirstName(firstName);
        userCreateRequest.setLastName(lastName);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userCreateRequest)))
                .andExpect(jsonPath("userId")
                        .exists())
                .andExpect(jsonPath("firstName")
                        .value(is(firstName)))
                .andExpect(jsonPath("lastName")
                        .value(is(lastName)))
                .andExpect(status().isCreated());
    }
}