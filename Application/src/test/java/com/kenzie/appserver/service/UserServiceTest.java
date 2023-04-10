package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByUserId() {
        // GIVEN
        String id = randomUUID().toString();

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(id);
        userRecord.setFirstName("ABC");
        userRecord.setLastName("XYZ");

        // WHEN
        when(userRepository.findById(id)).thenReturn(Optional.of(userRecord));
        User user = userService.findById(id);

        // THEN
        Assertions.assertNotNull(user, "The user is returned");
        assertEquals(userRecord.getUserId(), user.getUserId(), "The id matches");
        assertEquals(userRecord.getFirstName(), user.getFirstName(), "The first name matches");
        assertEquals(userRecord.getLastName(), user.getLastName(), "The last name matches");
    }


    @Test
    void addNewUser() {
        // GIVEN
        String id = randomUUID().toString();

        // Create a new user
        User user = new User(id, "ABC", "XYZ");

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(id);
        userRecord.setFirstName("ABC");
        userRecord.setLastName("XYZ");

        // WHEN
        when(userRepository.save(userRecord)).thenReturn(userRecord);

        User result = userService.addNewUser(user);

        // THEN
        verify(userRepository).save(userRecord);
        assertEquals(id, userRecord.getUserId());
        assertEquals("ABC", userRecord.getFirstName());
        assertEquals("XYZ", userRecord.getLastName());
        Assertions.assertSame(user, result);
    }

    @Test
    public void findAllUsers() {
        // GIVEN
        List<UserRecord> users = new ArrayList<>();
        UserRecord user1 = new UserRecord();
        user1.setUserId("1");
        user1.setFirstName("ABC");
        user1.setLastName("XYZ");
        users.add(user1);
        UserRecord user2 = new UserRecord();
        user2.setUserId("2");
        user2.setFirstName("AABB");
        user2.setLastName("XXYY");
        users.add(user2);

        //WHEN
        when(userRepository.findAll()).thenReturn(users);

        List<UserRecord> allUsers = userService.findAll();

        //THEN
        assertEquals(users.size(), allUsers.size());
        Assertions.assertTrue(allUsers.contains(user1));
        Assertions.assertTrue(allUsers.contains(user2));
        }
    }
